package com.mobile.nativeandroidapis.qr_code.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobile.nativeandroidapis.qr_code.viewmodel.QRCodeViewModel
import com.mobile.nativeandroidapis.router.Navigator
import com.mobile.nativeandroidapis.ui.screens.CustomAppBar
import com.mobile.nativeandroidapis.ui.screens.TitleText
import org.koin.androidx.compose.koinViewModel

@Composable
 fun DisplayScannedQRScreen(navigator: Navigator, qrCodeViewModel: QRCodeViewModel = koinViewModel()) {
        Scaffold(
            topBar = { CustomAppBar(title = "Scanned QR Details", onClick = { navigator.navigateBack() }
            ) }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                TitleText("Scanned QR Code is....\n +")
            }
        }
    }
