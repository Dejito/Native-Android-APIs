package com.mobile.nativeandroidapis.qr_code.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class QRCodeViewModel(): ViewModel() {

    private val _qrCodeData = MutableStateFlow("")
    val qrCodeData: StateFlow<String> = _qrCodeData

    fun setQrCodeData(qrCodeData: String) {
        _qrCodeData.update {
            qrCodeData
        }
    }




}