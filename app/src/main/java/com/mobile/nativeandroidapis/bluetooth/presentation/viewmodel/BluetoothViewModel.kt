package com.mobile.nativeandroidapis.bluetooth.presentation.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BluetoothViewModel(application: Application) : AndroidViewModel(application) {

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private val _devices = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    val devices = _devices.asStateFlow()

    private val _messages = MutableStateFlow<List<String>>(emptyList())
    val messages = _messages.asStateFlow()

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == BluetoothDevice.ACTION_FOUND) {
                val device: BluetoothDevice? =
                    intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                device?.let {
                    viewModelScope.launch {
                        _devices.value = _devices.value + it
                    }
                }
            }
        }
    }

    fun startDiscovery() {
        val ctx = getApplication<Application>()

        val hasPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(
                ctx, Manifest.permission.BLUETOOTH_SCAN
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(
                ctx, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }

        if (!hasPermission) {
            // skip safely
            return
        }

        _devices.value = emptyList()
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        ctx.registerReceiver(receiver, filter)
        @SuppressLint("MissingPermission") // 👈 applied directly here if you prefer
        bluetoothAdapter?.startDiscovery()
    }


    @SuppressLint("MissingPermission")
    fun stopDiscovery() {
        try {
            getApplication<Application>().unregisterReceiver(receiver)
        } catch (_: Exception) {
            // ignore if already unregistered
        }
        bluetoothAdapter?.cancelDiscovery()
    }

    fun appendMessage(msg: String) {
        viewModelScope.launch {
            _messages.value = _messages.value + msg
        }
    }

    @SuppressLint("MissingPermission")
    override fun onCleared() {
        super.onCleared()
        stopDiscovery()
    }


}
