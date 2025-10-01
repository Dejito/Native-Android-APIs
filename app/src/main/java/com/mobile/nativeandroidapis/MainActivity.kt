package com.mobile.nativeandroidapis

import android.app.ComponentCaller
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mobile.nativeandroidapis.nfc.presentation.view.NfcScreen
import com.mobile.nativeandroidapis.nfc.presentation.viewmodel.NFCViewModel
import com.mobile.nativeandroidapis.ui.theme.NativeAndroidAPIsTheme
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {

    private lateinit var nfcViewModel: NFCViewModel
    private var nfcAdapter: NfcAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        nfcViewModel = ViewModelProvider(this)[NFCViewModel::class.java]
        val isNFCAvailable = packageManager.hasSystemFeature(PackageManager.FEATURE_NFC)
        nfcViewModel.isNfcAvailable(isNFCAvailable)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NativeAndroidAPIsTheme {
                NfcScreen()
//                AppNavigator()
            }
        }
    }


    override fun onNewIntent(intent: Intent, caller: ComponentCaller) {
        super.onNewIntent(intent)

        val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        if (intent.action == NfcAdapter.ACTION_TAG_DISCOVERED) {
            val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            val messages = rawMsgs?.map { it as NdefMessage }?.toTypedArray()
            if (nfcViewModel.state.value.isWriting) {
                nfcViewModel.writeTag(tag)
            } else {
                nfcViewModel.readTag(tag, messages)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        nfcViewModel.updateIsEnabled(nfcAdapter?.isEnabled == true)

        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_MUTABLE
        )
        val filters = arrayOf(IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED))
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, filters, null)
    }


    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

}
