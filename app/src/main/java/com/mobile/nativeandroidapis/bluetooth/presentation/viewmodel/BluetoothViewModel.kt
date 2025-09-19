package com.mobile.nativeandroidapis.bluetooth.presentation.viewmodel

import android.Manifest
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.*
import androidx.annotation.RequiresPermission
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BluetoothViewModel(application: Application) : AndroidViewModel(application) {

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private val _devices = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    val devices = _devices.asStateFlow()

    private val _messages = MutableStateFlow<List<String>>(emptyList())
    val messages = _messages.asStateFlow()

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == BluetoothDevice.ACTION_FOUND) {
                val device: BluetoothDevice? =
                    intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                device?.let {
                    viewModelScope.launch {
                        _devices.value = _devices.value + it
                    }
                }
            }
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun startDiscovery() {
        _devices.value = emptyList()
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        getApplication<Application>().registerReceiver(receiver, filter)
        bluetoothAdapter?.startDiscovery()
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun stopDiscovery() {
        try {
            getApplication<Application>().unregisterReceiver(receiver)
        } catch (_: Exception) { }
        bluetoothAdapter?.cancelDiscovery()
    }

    fun appendMessage(msg: String) {
        viewModelScope.launch {
            _messages.value = _messages.value + msg
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    override fun onCleared() {
        super.onCleared()
        stopDiscovery()
    }


}
