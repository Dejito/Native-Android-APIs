//package com.mobile.nativeandroidapis.bluetooth.data
//
//import android.Manifest
//import android.annotation.SuppressLint
//import android.bluetooth.BluetoothAdapter
//import android.bluetooth.BluetoothDevice
//import android.bluetooth.BluetoothServerSocket
//import android.bluetooth.BluetoothSocket
//import android.content.pm.PackageManager
//import android.os.Build
//import android.util.Log
//import androidx.core.content.ContextCompat
//import kotlinx.coroutines.*
//import kotlinx.coroutines.flow.MutableSharedFlow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asSharedFlow
//import kotlinx.coroutines.flow.asStateFlow
//import java.io.InputStream
//import java.io.OutputStream
//import java.util.*
//
//class ConnectionManagerImpl(
//    private val scope: CoroutineScope,
//    private val adapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
//) : ConnectionManager {
//
//    private val _connectionState = MutableStateFlow<ConnectionManager.ConnectionState>(ConnectionManager.ConnectionState.Disconnected)
//    override val connectionState = _connectionState.asStateFlow()
//
//    private val _incomingMessages = MutableSharedFlow<String>(replay = 50)
//    override val incomingMessages = _incomingMessages.asSharedFlow()
//
//    private var socket: BluetoothSocket? = null
//    private var serverSocket: BluetoothServerSocket? = null
//    private var readJob: Job? = null
//    private var writeStream: OutputStream? = null
//
//    override suspend fun startServer(uuid: UUID) = withContext(Dispatchers.IO) {
//        try {
//            _connectionState.value = ConnectionManager.ConnectionState.Connecting
//            serverSocket = adapter?.listenUsingRfcommWithServiceRecord("BT_DEMO", uuid)
//            val clientSocket = serverSocket?.accept()
//            socket = clientSocket
//            setupStreamsAndReadLoop()
//            _connectionState.value = ConnectionManager.ConnectionState.Connected
//        } catch (e: Exception) {
//            _connectionState.value = ConnectionManager.ConnectionState.Error(e.localizedMessage ?: "server error")
//            Log.e("ConnMgr", "server error", e)
//        } finally {
//            serverSocket?.close()
//            serverSocket = null
//        }
//    }
//
//
//    override suspend fun connectAsClient(device: BluetoothDevice, uuid: UUID) = withContext(Dispatchers.IO) {
//        try {
//            _connectionState.value = ConnectionManager.ConnectionState.Connecting
//
//            val ctx = adapter?.context // ⚠️ BluetoothAdapter has no context; getApplicationContext from VM or pass in
//            val hasPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                ContextCompat.checkSelfPermission(
//                    ctx, Manifest.permission.BLUETOOTH_SCAN
//                ) == PackageManager.PERMISSION_GRANTED
//            } else {
//                true // pre-S, no BLUETOOTH_SCAN permission
//            }
//
//            // cancel discovery as it slows connection
//            if (hasPermission) {
//                try {
//                    @SuppressLint("MissingPermission")
//                    adapter?.cancelDiscovery()
//                } catch (se: SecurityException) {
//                    Log.w("ConnMgr", "cancelDiscovery failed: ${se.message}")
//                }
//            }
//
//            val sock = device.createRfcommSocketToServiceRecord(uuid)
//            sock.connect()
//            socket = sock
//            setupStreamsAndReadLoop()
//            _connectionState.value = ConnectionManager.ConnectionState.Connected
//        } catch (e: Exception) {
//            _connectionState.value = ConnectionManager.ConnectionState.Error(e.localizedMessage ?: "client error")
//            Log.e("ConnMgr", "client error", e)
//            try { socket?.close() } catch (_: Exception) {}
//            socket = null
//        }
//    }
//
//
//    private fun setupStreamsAndReadLoop() {
//        val s = socket ?: return
//        readJob?.cancel()
//        writeStream = s.outputStream
//        val input: InputStream = s.inputStream
//        readJob = scope.launch(Dispatchers.IO) {
//            val buffer = ByteArray(1024)
//            try {
//                while (isActive) {
//                    val read = input.read(buffer)
//                    if (read <= 0) continue
//                    val text = String(buffer, 0, read)
//                    _incomingMessages.emit(text)
//                }
//            } catch (e: Exception) {
//                Log.e("ConnMgr", "read loop error", e)
//                _connectionState.value = ConnectionManager.ConnectionState.Error(e.localizedMessage ?: "read error")
//            }
//        }
//    }
//
//    override suspend fun send(data: ByteArray) = withContext(Dispatchers.IO) {
//        try {
//            writeStream?.write(data)
//            writeStream?.flush()
//        } catch (e: Exception) {
//            Log.e("ConnMgr", "send error", e)
//            _connectionState.value = ConnectionManager.ConnectionState.Error(e.localizedMessage ?: "send error")
//        }
//    }
//
//    override fun disconnect() {
//        readJob?.cancel()
//        try { socket?.close() } catch (_: Exception) {}
//        try { serverSocket?.close() } catch (_: Exception) {}
//        socket = null
//        serverSocket = null
//        _connectionState.value = ConnectionManager.ConnectionState.Disconnected
//    }
//}
