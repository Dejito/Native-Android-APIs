package com.mobile.nativeandroidapis.nfc.data


import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import com.mobile.nativeandroidapis.nfc.presentation.viewmodel.NFCViewModel

class NfcManager(
    private val activity: Activity,
    private val viewModel: NFCViewModel
  ) {
    private val nfcAdapter: NfcAdapter? = NfcAdapter.getDefaultAdapter(activity)

    private val pendingIntent = PendingIntent.getActivity(
        activity, 0,
        Intent(activity, activity.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
        PendingIntent.FLAG_MUTABLE
    )

    private val intentFilters = arrayOf(
        IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
    )

    fun onResume() {
        nfcAdapter?.enableForegroundDispatch(
            activity,
            pendingIntent,
            intentFilters,
            null
        )
        viewModel.setNfcEnabledOnDeviceCheck(nfcAdapter?.isEnabled == true)
    }

    fun onPause() {
        nfcAdapter?.disableForegroundDispatch(activity)
    }

    fun handleIntent(intent: Intent) {
        when (intent.action) {
            NfcAdapter.ACTION_NDEF_DISCOVERED,
            NfcAdapter.ACTION_TAG_DISCOVERED,
            NfcAdapter.ACTION_TECH_DISCOVERED -> {
                val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
                val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
                val messages = rawMsgs?.map { it as NdefMessage }?.toTypedArray()

                if (viewModel.nfcState.value.isWriting) {
                    viewModel.writeTag(tag)
                } else {
                    viewModel.readTag(tag, messages)
                }
            }
        }
    }
}
