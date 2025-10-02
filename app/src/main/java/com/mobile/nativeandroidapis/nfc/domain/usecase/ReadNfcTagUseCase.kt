package com.mobile.nativeandroidapis.nfc.domain.usecase

import android.nfc.NdefMessage
import android.nfc.Tag
import com.mobile.nativeandroidapis.nfc.domain.model.NfcTagData

class ReadNfcTagUseCase {
    @OptIn(ExperimentalStdlibApi::class)
    fun execute(tag: Tag?, rawMessages: Array<NdefMessage>?): NfcTagData? {
        if (tag == null) return null

        val techList = tag.techList.joinToString()
        val parsedMessages = mutableListOf<String>()

        rawMessages?.forEach { msg ->
            msg.records.forEach { record ->
                val type = record.toMimeType() ?: "unknown"
                val payload = record.payload.decodeToString()
                parsedMessages.add("[$type]: $payload")
            }
        }

        return NfcTagData(
            tagId = tag.id.toHexString(),
            payload = parsedMessages.joinToString(",\n"),
            techList = techList
        )
    }
}
