package com.mobile.nativeandroidapis.qr_code.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobile.nativeandroidapis.router.Navigator
import com.mobile.nativeandroidapis.ui.screens.AppHomePageCard
import com.mobile.nativeandroidapis.ui.screens.CustomAppBar


@Composable
fun SelectQRCodeOption(navigator: Navigator) {
    Scaffold(
        topBar = { CustomAppBar(title = "Select Option", onClick = { navigator.navigateBack() }
        ) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            AppHomePageCard(title = "Scan QR Code", onClick = { navigator.navToQRCodeScanner() })
            AppHomePageCard(title = "Generate QR Code", onClick = { navigator.navToQRCodeScreen() })
        }
    }
}
