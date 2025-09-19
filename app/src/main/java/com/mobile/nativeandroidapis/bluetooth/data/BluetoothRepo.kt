package com.mobile.nativeandroidapis.bluetooth.data

import android.bluetooth.BluetoothDevice
import com.mobile.nativeandroidapis.bluetooth.domain.ConnectionState
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

interface BluetoothRepository {
    val connectionState: StateFlow<ConnectionState>

    // 🔹 Newly added
    val devices: StateFlow<List<BluetoothDevice>>

    suspend fun startDiscovery()

    suspend fun connect(device: BluetoothDevice, uuid: UUID): Boolean
    suspend fun send(data: ByteArray)
    suspend fun disconnect()
}
