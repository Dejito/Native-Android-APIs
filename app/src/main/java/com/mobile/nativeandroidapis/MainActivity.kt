package com.mobile.nativeandroidapis

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.mobile.nativeandroidapis.nfc.data.NfcManager
import com.mobile.nativeandroidapis.nfc.presentation.view.NfcScreen
import com.mobile.nativeandroidapis.nfc.presentation.viewmodel.NFCViewModel
import com.mobile.nativeandroidapis.router.AppNavigator
import com.mobile.nativeandroidapis.ui.theme.NativeAndroidAPIsTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: NFCViewModel
    private lateinit var nfcManager: NfcManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[NFCViewModel::class.java]
        nfcManager = NfcManager(this, viewModel)

        val isNFCAvailable = packageManager.hasSystemFeature(android.content.pm.PackageManager.FEATURE_NFC)
        viewModel.isNfcAvailable(isNFCAvailable)

        setContent {
            NativeAndroidAPIsTheme {
                AppNavigator()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        nfcManager.onResume()
    }

    override fun onPause() {
        super.onPause()
        nfcManager.onPause()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        nfcManager.handleIntent(intent)
    }
}
