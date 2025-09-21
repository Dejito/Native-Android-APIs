package com.mobile.nativeandroidapis.bluetooth.presentation.viewmodel

import com.mobile.nativeandroidapis.bluetooth.domain.BluetoothDevice
import com.mobile.nativeandroidapis.bluetooth.domain.BluetoothMessage


data class BluetoothUiState(
    val scannedDevices: List<BluetoothDevice> = emptyList(),
    val pairedDevices: List<BluetoothDevice> = emptyList(),
    val isConnected: Boolean = false,
    val isConnecting: Boolean = false,
    val errorMessage: String? = null,
    val messages: List<BluetoothMessage> = emptyList()
)
