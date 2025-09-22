package com.mobile.nativeandroidapis.qr_code.viewmodel

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val qrCodeModule = module {
    viewModel { QRCodeViewModel() }
}