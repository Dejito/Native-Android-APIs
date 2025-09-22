package com.mobile.nativeandroidapis.di

import com.mobile.nativeandroidapis.bluetooth.di.bluetoothModule
import com.mobile.nativeandroidapis.qr_code.viewmodel.qrCodeModule


val sharedKoinModules = listOf(
    bluetoothModule,
    qrCodeModule
)