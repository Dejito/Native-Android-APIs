// ui/BluetoothScreen.kt
package com.mobile.nativeandroidapis.bluetooth.presentation.view

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.bluetooth.presentation.BluetoothViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.UUID

@Composable
fun BluetoothScreen(viewModel: BluetoothViewModel = koinViewModel()) {
    val context = LocalContext.current
    val devices by viewModel.devices.collectAsState()
    val state by viewModel.connectionState.collectAsState()
    var message by remember { mutableStateOf("") }
    var requestEnableBluetooth by remember { mutableStateOf(false) }

    // Permissions needed
    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT
        )
    } else {
        arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    val permissionsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val granted = perms.values.all { it }
        if (!granted) {
            Toast.makeText(context, "Bluetooth permissions denied", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.startDiscovery()
        }
    }

    val enableBluetoothLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.startDiscovery()
        } else {
            Toast.makeText(context, "Bluetooth not enabled", Toast.LENGTH_SHORT).show()
        }
    }

    // Launch enable Bluetooth intent when requested by ViewModel
    LaunchedEffect(requestEnableBluetooth) {
        if (requestEnableBluetooth) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            enableBluetoothLauncher.launch(intent)
            requestEnableBluetooth = false
        }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {

        Button(onClick = {
            permissionsLauncher.launch(permissions)
        }) {
            Text("Scan for Devices")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Connection State: $state")

        Spacer(modifier = Modifier.height(16.dp))
        Text("Discovered Devices:")
        LazyColumn {
            items(devices) { device ->
                Text(
                    text = device.name ?: "Unknown (${device.address})",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
                            viewModel.connectToDevice(device, uuid)
                        }
                        .padding(8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Message to send") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = { viewModel.sendMessage(message) }) { Text("Send") }
            Spacer(Modifier.width(16.dp))
            Button(onClick = { viewModel.disconnect() }) { Text("Disconnect") }
        }
    }
}
