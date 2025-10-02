package com.mobile.nativeandroidapis.nfc.domain.model


data class NfcTagData(
    val tagId: String,
    val payload: String,
    val techList: String? = null
)
