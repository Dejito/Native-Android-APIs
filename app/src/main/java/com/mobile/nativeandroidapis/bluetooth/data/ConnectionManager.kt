//package com.mobile.nativeandroidapis.bluetooth.data
//
//import android.bluetooth.BluetoothDevice
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.flow.SharedFlow
//import kotlinx.coroutines.flow.StateFlow
//import java.util.*
//
//interface ConnectionManager {
//    sealed class ConnectionState {
//        object Disconnected : ConnectionState()
//        object Connecting : ConnectionState()
//        object Connected : ConnectionState()
//        data class Error(val reason: String) : ConnectionState()
//    }
//
//    val connectionState: StateFlow<ConnectionState>
//    val incomingMessages: SharedFlow<String>
//
//    suspend fun startServer(uuid: UUID)
//    suspend fun connectAsClient(device: BluetoothDevice, uuid: UUID)
//    suspend fun send(data: ByteArray)
//    fun disconnect()
//}
