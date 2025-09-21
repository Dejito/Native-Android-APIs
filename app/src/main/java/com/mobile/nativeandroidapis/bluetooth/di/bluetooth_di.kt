package com.mobile.nativeandroidapis.bluetooth.di

import android.bluetooth.BluetoothAdapter
import com.mobile.nativeandroidapis.bluetooth.data.AndroidBluetoothController
import com.mobile.nativeandroidapis.bluetooth.presentation.viewmodel.BluetoothViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

//val bluetoothModule = module {
//    single<BluetoothRepository> { BluetoothRepositoryImpl(BluetoothAdapter.getDefaultAdapter()) }
//    single { BluetoothViewModel(get()) }
//}

//single { SLRepositoryImpl() }

val bluetoothModule = module {
    single { AndroidBluetoothController(
        context = androidContext()
    ) }
    viewModel { BluetoothViewModel(get()) } // use viewModel DSL instead of single
}
