package com.mobile.nativeandroidapis

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.mobile.nativeandroidapis.nfc.data.NfcManager
import com.mobile.nativeandroidapis.nfc.presentation.viewmodel.NFCViewModel
import com.mobile.nativeandroidapis.router.AppNavigator
import com.mobile.nativeandroidapis.ui.theme.NativeAndroidAPIsTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val nfcViewModel: NFCViewModel by viewModel() // <-- from Koin
    private lateinit var nfcManager: NfcManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nfcManager = NfcManager(this, nfcViewModel)

        val isNFCAvailable = packageManager.hasSystemFeature(
            android.content.pm.PackageManager.FEATURE_NFC
        )
        nfcViewModel.setNfcSupportedByDeviceCheck(isNFCAvailable)

        setContent {
            NativeAndroidAPIsTheme {
                AppNavigator() // Composables can get same ViewModel via koinViewModel()
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
