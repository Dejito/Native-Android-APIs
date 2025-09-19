// presentation/BluetoothViewModel.kt
package com.example.bluetooth.presentation

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.nativeandroidapis.bluetooth.data.BluetoothRepository
import com.mobile.nativeandroidapis.bluetooth.domain.ConnectionState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class BluetoothViewModel(
    private val repository: BluetoothRepository
) : ViewModel() {

    // 🔹 Expose connection state
    val connectionState: StateFlow<ConnectionState> =
        repository.connectionState.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            ConnectionState.Idle
        )

    // 🔹 Expose discovered devices
    val devices: StateFlow<List<BluetoothDevice>> =
        repository.devices.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )

    fun startDiscovery() {
        viewModelScope.launch {
            repository.startDiscovery()
        }
    }

    fun connectToDevice(device: BluetoothDevice, uuid: UUID) {
        viewModelScope.launch {
            repository.connect(device, uuid)
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            repository.send(message.toByteArray())
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            repository.disconnect()
        }
    }
}
