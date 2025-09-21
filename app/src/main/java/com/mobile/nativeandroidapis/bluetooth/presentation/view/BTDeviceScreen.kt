package com.mobile.nativeandroidapis.bluetooth.presentation.view

import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.nativeandroidapis.bluetooth.presentation.viewmodel.BluetoothViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun DeviceScreen(bluetoothViewModel: BluetoothViewModel = koinViewModel()) {

    val devices = bluetoothViewModel.devices.collectAsState().value

    Scaffold(
        topBar = {},
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                BluetoothDeviceList(
                    pairedDevices = devices.pairedDevices,
                    scannedDevices = devices.scannedDevices,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = { bluetoothViewModel.startScan() }) {
                        Text(text = "Start scan")
                    }
                    Button(onClick = { bluetoothViewModel.stopScan() }) {
                        Text(text = "Stop scan")
                    }
                }
            }
        }
    )

}

@Composable
fun BluetoothDeviceList(
    pairedDevices: List<com.mobile.nativeandroidapis.bluetooth.domain.BluetoothDevice>,
    scannedDevices: List<com.mobile.nativeandroidapis.bluetooth.domain.BluetoothDevice>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Text(
                text = "Paired Devices",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(pairedDevices) { device ->
            Text(
                text = device.name ?: "(No name)",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() }
                    .padding(16.dp)
            )
        }

        item {
            Text(
                text = "Scanned Devices",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(scannedDevices) { device ->
            Text(
                text = device.name ?: "(No name)",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() }
                    .padding(16.dp)
            )
        }
    }
}