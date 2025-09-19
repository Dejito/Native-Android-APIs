package com.mobile.nativeandroidapis.bluetooth.domain

data class BluetoothDeviceDomain(
    val name: String?,
    val address: String
)

sealed class ConnectionState {
    object Idle : ConnectionState()
    object Connecting : ConnectionState()
    object Connected : ConnectionState()
    data class Error(val message: String) : ConnectionState()
    object Disconnected : ConnectionState()
}
