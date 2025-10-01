package com.mobile.nativeandroidapis.bluetooth.presentation.viewmodel

import com.mobile.nativeandroidapis.bluetooth.data.BluetoothControllerImplementation
import com.mobile.nativeandroidapis.bluetooth.domain.BluetoothController
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val bluetoothModule = module {
    single<BluetoothController> { BluetoothControllerImplementation(androidContext()) }
    single { BluetoothViewModel(get()) }
}

