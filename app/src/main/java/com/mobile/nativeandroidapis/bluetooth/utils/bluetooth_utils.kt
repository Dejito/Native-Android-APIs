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

    override fun run() {
        try {
            val buffer = ByteArray(1024)
            val bytes = inputStream.read(buffer)
            val text = String(buffer, 0, bytes)
            viewModel.appendMessage("Received: $text")
        } catch (e: Exception) {
            Log.e("BluetoothServer", "Error", e)
        } finally {
            inputStream.close()
            outputStream.close()
            socket.close()
        }
    }
}
