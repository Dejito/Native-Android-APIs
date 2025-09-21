package com.mobile.nativeandroidapis.bluetooth.presentation.view

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.nativeandroidapis.bluetooth.domain.BluetoothDeviceDomain
import com.mobile.nativeandroidapis.bluetooth.presentation.viewmodel.BluetoothViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun BluetoothDevicesScreen(
    bluetoothViewModel: BluetoothViewModel = koinViewModel()
) {

    val device = bluetoothViewModel.devices.collectAsState().value

    when {
        device.isConnecting -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text(text = "Connecting...")
            }
        }
        else -> {
            DeviceScreen()
        }
    }
}

@Composable
fun DeviceScreen(
    bluetoothViewModel: BluetoothViewModel = koinViewModel()
) {

    val devices = bluetoothViewModel.devices.collectAsState().value

    val context = LocalContext.current
    val bluetoothManager = remember {
        context.getSystemService(BluetoothManager::class.java)
    }
    val bluetoothAdapter = remember {
        bluetoothManager?.adapter
    }
    val isBluetoothEnabled = bluetoothAdapter?.isEnabled == true

    // Launcher to enable Bluetooth
    val enableBluetoothLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { /* no result needed */ }

    // Launcher for permissions
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val canEnableBluetooth =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                perms[Manifest.permission.BLUETOOTH_CONNECT] == true
            } else true

        if (canEnableBluetooth && !isBluetoothEnabled) {
            enableBluetoothLauncher.launch(
                Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            )
        }
    }

    // Request permissions once
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.ACCESS_FINE_LOCATION // required for discovery
                )
            )
        }
    }

    LaunchedEffect(key1 = devices.isConnected) {
        if (devices.isConnected) {
            context.displayToastMessage("Device connected!")
        }
    }


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
                    Button(onClick = {
                        bluetoothViewModel.startScan()
                    }) {
                        Text(text = "Start scan")
                    }
                    Button(onClick = {
                        bluetoothViewModel.stopScan()
                    }) {
                        Text(text = "Stop scan")
                    }
                }
            }
        }
    )
}

@Composable
fun BluetoothDeviceList(
    pairedDevices: List<BluetoothDeviceDomain>,
    scannedDevices: List<BluetoothDeviceDomain>,
    onClick: (BluetoothDeviceDomain) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            Text(
                "Paired Devices",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
        items(pairedDevices) { device ->
            Text(
                text = device.name ?: "(No name)",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(device) }
                    .padding(12.dp)
            )
        }
        item {
            Text(
                "Scanned Devices",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
        items(scannedDevices) { device ->
            Text(
                text = device.name ?: "(No name)",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(device) }
                    .padding(12.dp)
            )
        }
    }
}
