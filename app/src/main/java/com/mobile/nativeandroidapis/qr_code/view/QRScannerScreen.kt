package com.mobile.nativeandroidapis.qr_code.view

import CameraPreview
import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mobile.nativeandroidapis.router.Navigator
import com.mobile.nativeandroidapis.ui.screens.CustomAppBar
import com.mobile.nativeandroidapis.ui.screens.TitleText
import com.mobile.nativeandroidapis.R
import com.mobile.nativeandroidapis.qr_code.viewmodel.QRCodeViewModel
import com.mobile.nativeandroidapis.ui.screens.GIFImage
import com.mobile.nativeandroidapis.ui.screens.displayToastMessage
import org.koin.androidx.compose.koinViewModel


@Composable
fun QRCodeScannerScreen(
    navigator: Navigator, qrCodeViewModel: QRCodeViewModel
    = koinViewModel()
) {

    var hasPermission by remember { mutableStateOf(false) }

    val context = LocalContext.current

    PermissionRequestDialog(
        permission = Manifest.permission.CAMERA,
        onResult = { granted ->
            hasPermission = granted
        }
    )
    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Scan QR Code", textColor = Color.Black, backgroundColor = Color.White,
                onClick = { navigator.navigateBack() }, tint = Color.Black
            )
        },
    ) { paddingValues ->
        if (hasPermission)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(painter = painterResource(id = R.drawable.runteller), alpha = 1f)
                .padding(horizontal = 10.dp)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleText(
                text = "Hold your camera over a QR Code to scan",
                color = Color.Black, fontSize = 13, fontWeight = FontWeight.W500,
            )
            TitleText(
                text = "Hold your camera over a QR Code to scan \n" + "QR Code to scan",
                color = Color.White, fontSize = 13, fontWeight = FontWeight.W500,
            )
            Box {
                CameraPreview(
                    modifier = Modifier,
                    encryptedDetail = { details ->
                        try {
                            qrCodeViewModel.setQrCodeData(details)
                            navigator.navToDisplayScannedQRScreen()
                        } catch (e: Exception) {
                            e.printStackTrace()
                            context.displayToastMessage(e.toString())
                            navigator.navigateBack()
                        }
                    },
                    onFailed = {
                        context.displayToastMessage("Something went wrong!")
                        navigator.navigateBack()
                    },
                    navigator = navigator
                )

                GIFImage(gifImage = R.drawable.scan, modifier = Modifier.size(400.dp))
            }

        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                TitleText("Camera permission required to scan QR code")
            }
        }
    }

}

