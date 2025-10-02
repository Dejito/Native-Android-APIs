package com.mobile.nativeandroidapis.nfc.presentation.viewmodel

import com.mobile.nativeandroidapis.nfc.domain.model.NfcTagData

data class NfcUiState(
    val isAvailable: Boolean = false,
    val isEnabled: Boolean = false,
    val isWriting: Boolean = false,
    val lastTag: NfcTagData? = null,
    val messageToWrite: String = "",
    val records: String = "",
    val techList: String = ""
)