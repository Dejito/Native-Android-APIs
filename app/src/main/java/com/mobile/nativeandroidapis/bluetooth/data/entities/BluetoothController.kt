package com.mobile.nativeandroidapis.bluetooth.data.entities

import com.plcoding.bluetoothchat.domain.chat.BluetoothDevice
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {
    val scannedDevices: StateFlow<List<BluetoothDevice>>
    val pairedDevices: StateFlow<List<BluetoothDevice>>

    fun startDiscovery()
    fun stopDiscovery()

    fun release()
}