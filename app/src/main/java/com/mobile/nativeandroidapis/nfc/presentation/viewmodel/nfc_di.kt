package com.mobile.nativeandroidapis.nfc.presentation.viewmodel

import com.mobile.nativeandroidapis.bluetooth.presentation.viewmodel.BluetoothViewModel
import com.mobile.nativeandroidapis.nfc.data.NfcRepository
import org.koin.dsl.module

val nfcModule = module {
    single { NfcRepository() }
    single { NFCViewModel(get()) }

}