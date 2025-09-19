package com.mobile.nativeandroidapis.bluetooth.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobile.nativeandroidapis.bluetooth.presentation.viewmodel.BluetoothViewModel


@Composable
fun BluetoothScreen(viewModel: BluetoothViewModel = viewModel()) {
    val devices by viewModel.devices.collectAsState()
    val messages by viewModel.messages.collectAsState()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = { viewModel.startDiscovery() }) {
            Text("Scan for Devices")
        }

        Spacer(Modifier.height(16.dp))

        Text("Discovered Devices:", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(devices) { device ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            // TODO: Connect using BluetoothClient(device)
                        }
                        .padding(8.dp)
                ) {
                    Text(device.name ?: "Unknown")
                    Spacer(Modifier.weight(1f))
                    Text(device.address)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Text("Messages:", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(messages) { msg ->
                Text(msg, Modifier.padding(4.dp))
            }
        }
    }
}
