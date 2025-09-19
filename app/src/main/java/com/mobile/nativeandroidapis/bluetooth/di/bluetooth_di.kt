package com.mobile.nativeandroidapis.bluetooth.di

import android.bluetooth.BluetoothAdapter
import com.example.bluetooth.presentation.BluetoothViewModel
import com.mobile.nativeandroidapis.bluetooth.data.BluetoothRepository
import com.mobile.nativeandroidapis.bluetooth.data.BluetoothRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

//val bluetoothModule = module {
//    single<BluetoothRepository> { BluetoothRepositoryImpl(BluetoothAdapter.getDefaultAdapter()) }
//    single { BluetoothViewModel(get()) }
//}
val bluetoothModule = module {
    single<BluetoothRepository> {
        BluetoothRepositoryImpl(
            androidContext(),               // pass Context
            BluetoothAdapter.getDefaultAdapter() // pass BluetoothAdapter
        )
    }
    viewModel { BluetoothViewModel(get()) } // use viewModel DSL instead of single
}
