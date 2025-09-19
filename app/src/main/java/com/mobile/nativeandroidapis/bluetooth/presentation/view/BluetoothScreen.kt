package com.mobile.nativeandroidapis.bluetooth.presentation.view

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mobile.nativeandroidapis.bluetooth.presentation.viewmodel.BluetoothViewModel


@Composable
fun BluetoothScreen(viewModel: BluetoothViewModel = viewModel()) {
    val context = LocalContext.current

    // List of permissions needed
    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        listOf(
            android.Manifest.permission.BLUETOOTH_SCAN,
            android.Manifest.permission.BLUETOOTH_CONNECT
        )
    } else {
        listOf(
            android.Manifest.permission.BLUETOOTH,
            android.Manifest.permission.BLUETOOTH_ADMIN,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    val multiplePermissionsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val granted = perms.values.all { it }
        if (!granted) {
            Toast.makeText(context, "Bluetooth permissions denied", Toast.LENGTH_SHORT).show()
        }
    }

    val devices by viewModel.devices.collectAsState()
    val messages by viewModel.messages.collectAsState()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = {
            multiplePermissionsLauncher.launch(permissions.toTypedArray())
            viewModel.startDiscovery()
        }) {
            Text("Scan for Devices")
        }

        Spacer(Modifier.height(16.dp))

        Text("Discovered Devices:")
        LazyColumn {
            items(devices) { device ->
                Text("${device.name ?: "Unknown"} - ${device.address}")
            }
        }

        Spacer(Modifier.height(16.dp))

        Text("Messages:")
        LazyColumn {
            items(messages) { msg ->
                Text(msg)
            }
        }
    }
}
