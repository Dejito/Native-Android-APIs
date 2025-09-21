package com.mobile.nativeandroidapis.bluetooth.presentation.viewmodel

import com.mobile.nativeandroidapis.bluetooth.domain.BluetoothDevice


data class BluetoothUiState(
    val scannedDevices: List<BluetoothDevice> = emptyList(),
    val pairedDevices: List<BluetoothDevice> = emptyList(),
)

