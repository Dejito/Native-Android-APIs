package com.mobile.nativeandroidapis.bluetooth.di

import com.mobile.nativeandroidapis.bluetooth.presentation.viewmodel.BluetoothViewModel
import org.koin.dsl.module

val bluetoothModule = module {
    single { BluetoothViewModel(get()) }
}
