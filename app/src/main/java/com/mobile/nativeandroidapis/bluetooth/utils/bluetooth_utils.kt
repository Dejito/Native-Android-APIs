package com.mobile.nativeandroidapis.bluetooth.utils

import android.bluetooth.BluetoothSocket
import android.util.Log
import com.mobile.nativeandroidapis.bluetooth.presentation.viewmodel.BluetoothViewModel

class BluetoothServer(
    private val viewModel: BluetoothViewModel,
    private val socket: BluetoothSocket
) : Thread() {
    private val inputStream = socket.inputStream
    private val outputStream = socket.outputStream


}
