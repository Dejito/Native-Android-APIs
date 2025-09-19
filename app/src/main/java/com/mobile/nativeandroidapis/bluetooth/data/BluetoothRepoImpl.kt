package com.mobile.nativeandroidapis.bluetooth.data

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.mobile.nativeandroidapis.bluetooth.domain.ConnectionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

class BluetoothRepositoryImpl(
    private val context: Context,
    private val adapter: BluetoothAdapter
) : BluetoothRepository {

    private var socket: BluetoothSocket? = null
    private var output: OutputStream? = null
    private var input: InputStream? = null

    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Idle)
    override val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private val _devices = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    override val devices: StateFlow<List<BluetoothDevice>> = _devices.asStateFlow()

    // Add a new StateFlow to tell ViewModel to request enabling Bluetooth
    private val _requestEnableBluetooth = MutableStateFlow(false)
    val requestEnableBluetooth: StateFlow<Boolean> = _requestEnableBluetooth.asStateFlow()

    fun checkBluetoothEnabled(): Boolean {
        return adapter?.isEnabled == true
    }

    fun signalEnableBluetooth() {
        _requestEnableBluetooth.value = true
    }


    private val discoveryReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctx: Context?, intent: Intent?) {
            if (intent?.action == BluetoothDevice.ACTION_FOUND) {
                val device: BluetoothDevice? =
                    intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                device?.let {
                    _devices.value = _devices.value + it
                }
            }
        }
    }

    override suspend fun startDiscovery() = withContext(Dispatchers.IO) {
        try {
            // ✅ Safe cancelDiscovery with explicit handling
            try {
                if (adapter == null) return@withContext

                if (!checkBluetoothEnabled()) {
                    signalEnableBluetooth()
                    return@withContext
                }

            } catch (se: SecurityException) {
                Log.w("BluetoothRepo", "cancelDiscovery failed: ${se.localizedMessage}")
            }

            // ✅ Permission check before discovery
            val hasPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_SCAN
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            }

            if (!hasPermission) {
                Log.w("BluetoothRepo", "Missing Bluetooth scan/location permission")
                return@withContext
            }

            _devices.value = emptyList()
            val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            context.registerReceiver(discoveryReceiver, filter)

            @Suppress("MissingPermission")
            adapter.startDiscovery()
        } catch (e: Exception) {
            Log.e("BluetoothRepo", "Discovery failed", e)
        }
    }

    override suspend fun connect(device: BluetoothDevice, uuid: UUID): Boolean = withContext(Dispatchers.IO) {
        try {
            _connectionState.value = ConnectionState.Connecting

            try {
                adapter.cancelDiscovery()
            } catch (se: SecurityException) {
                Log.w("BluetoothRepo", "cancelDiscovery failed: ${se.localizedMessage}")
            }

            val sock = device.createRfcommSocketToServiceRecord(uuid)
            sock.connect()
            socket = sock
            output = sock.outputStream
            input = sock.inputStream
            _connectionState.value = ConnectionState.Connected
            true // ✅ success
        } catch (e: Exception) {
            _connectionState.value = ConnectionState.Error(e.localizedMessage ?: "Connection failed")
            try {
                socket?.close()
            } catch (_: Exception) {
            }
            socket = null
            false // ❌ failed
        }
    }

    override suspend fun send(data: ByteArray): Unit = withContext(Dispatchers.IO) {
        try {
            output?.write(data)
        } catch (e: Exception) {
            _connectionState.value =
                ConnectionState.Error("Send failed: ${e.localizedMessage}")
        }
    }

    override suspend fun disconnect() = withContext(Dispatchers.IO) {
        try {
            socket?.close()
            socket = null
            _connectionState.value = ConnectionState.Disconnected
            try {
                context.unregisterReceiver(discoveryReceiver)
            } catch (_: Exception) {
            }
        } catch (e: Exception) {
            _connectionState.value =
                ConnectionState.Error("Disconnect failed: ${e.localizedMessage}")
        }
    }
}
