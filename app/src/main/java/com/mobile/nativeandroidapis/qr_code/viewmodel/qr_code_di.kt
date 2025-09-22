package com.mobile.nativeandroidapis.qr_code.viewmodel

import org.koin.dsl.module


val qrCodeModule = module {
    single { QRCodeViewModel() }
}