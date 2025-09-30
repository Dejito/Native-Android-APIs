package com.mobile.nativeandroidapis.nfc.data


import android.nfc.NdefMessage
import android.nfc.Tag
import com.mobile.nativeandroidapis.nfc.domain.model.NfcTagData
import com.mobile.nativeandroidapis.nfc.domain.usecase.ReadNfcTagUseCase
import com.mobile.nativeandroidapis.nfc.domain.usecase.WriteNfcTagUseCase


class NfcRepository(
    private val readUseCase: ReadNfcTagUseCase = ReadNfcTagUseCase(),
    private val writeUseCase: WriteNfcTagUseCase = WriteNfcTagUseCase()
) {

    fun readTag(tag: Tag?, messages: Array<NdefMessage>?): NfcTagData? {
        return readUseCase.execute(tag, messages)
    }

    fun writeTag(tag: Tag?, text: String): Boolean {
        return writeUseCase.execute(tag, text)
    }
}
