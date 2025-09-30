package com.mobile.nativeandroidapis.nfc.domain.usecase


import android.nfc.NdefMessage
import android.nfc.Tag
import android.nfc.tech.Ndef
import com.mobile.nativeandroidapis.nfc.domain.model.NfcTagData

class ReadNfcTagUseCase {
    fun execute(tag: Tag?, rawMessages: Array<NdefMessage>?): NfcTagData? {
        if (tag == null) return null

        val techList = tag.techList.joinToString()
        val parsedMessages = mutableListOf<String>()

        rawMessages?.map { msg ->
            msg.records.map { record ->
                val payload = record.payload.decodeToString()
                parsedMessages.add(payload)
            }
        }

        return NfcTagData(
            tagId = tag.id.joinToString(""),
            payload = parsedMessages.joinToString(", "),
            techList = techList
        )
    }
}
