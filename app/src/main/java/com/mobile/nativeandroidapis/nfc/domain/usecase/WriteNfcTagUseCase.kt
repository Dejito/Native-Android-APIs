package com.mobile.nativeandroidapis.nfc.domain.usecase


import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NdefRecord.TNF_ABSOLUTE_URI
import android.nfc.Tag
import android.nfc.tech.Ndef
import java.nio.charset.Charset

class WriteNfcTagUseCase {
    fun execute(tag: Tag?, text: String): Boolean {
        val ndef = Ndef.get(tag) ?: return false
        val record = NdefRecord.createMime("text/plain", text.toByteArray(Charset.forName("US-ASCII")))
        val message = NdefMessage(arrayOf(record))

        return try {
            if (ndef.maxSize < message.toByteArray().size) return false
            ndef.connect()
            ndef.writeNdefMessage(message)
            ndef.close()
            true
        } catch (e: Exception) {
            false
        }
    }
}

//val uriRecord = ByteArray(0).let { emptyByteArray ->
//    NdefRecord(
//        TNF_ABSOLUTE_URI,
//        "https://developer.android.com/index.html".toByteArray(Charset.forName("US-ASCII")),
//        emptyByteArray,
//        emptyByteArray
//    )
//}