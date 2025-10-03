package com.mobile.nativeandroidapis.di

import com.mobile.nativeandroidapis.bluetooth.presentation.viewmodel.bluetoothModule
import com.mobile.nativeandroidapis.nfc.presentation.viewmodel.nfcModule
import com.mobile.nativeandroidapis.qr_code.viewmodel.qrCodeModule
import com.mobile.nativeandroidapis.sqlite_cypher.presentation.viewmodel.sqlCipherModule


val sharedKoinModules = listOf(
    bluetoothModule,
    qrCodeModule,
    nfcModule,
    sqlCipherModule
)