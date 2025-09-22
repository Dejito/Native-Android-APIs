package com.mobile.nativeandroidapis.bluetooth.presentation.viewmodel

import com.mobile.nativeandroidapis.bluetooth.data.AndroidBluetoothController
import com.mobile.nativeandroidapis.bluetooth.domain.BluetoothController
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val bluetoothModule = module {
    single<BluetoothController> { AndroidBluetoothController(androidContext()) }
    viewModel { BluetoothViewModel(get()) }
}

