package com.mobile.nativeandroidapis.qr_code

import CameraPreview
import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.nativeandroidapis.router.Navigator
import com.mobile.nativeandroidapis.ui.screens.CustomAppBar
import com.mobile.nativeandroidapis.ui.screens.TitleText
import java.util.regex.Pattern
import com.mobile.nativeandroidapis.R
import com.mobile.nativeandroidapis.ui.screens.GIFImage
import com.mobile.nativeandroidapis.ui.screens.displayToastMessage


@Composable
fun QRCodeScannerScreen(navigator: Navigator, ) {
    var statusText by remember { mutableStateOf("") }

    val context = LocalContext.current

    PermissionRequestDialog(
        permission = Manifest.permission.CAMERA,
        onResult = { isGranted ->
            statusText = if (isGranted) {
                "Scan QR code now!"
            } else {
                "No camera permission!"
            }
        },
    )
    Scaffold(
        topBar = {
            CustomAppBar(title = "Scan QR Code", textColor = Color.Black, backgroundColor = Color.White,
                onClick = {navigator.navigateBack()}, tint = Color.Black
            )
        },
    ) {
            paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(painter = painterResource(id = R.drawable.runteller), alpha = 1f,)
                .padding(horizontal = 10.dp)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleText(text = "Hold your camera over a QR Code to scan",
                color = Color.Black, fontSize = 13, fontWeight = FontWeight.W500,
            )
            TitleText(text = "Hold your camera over a QR Code to scan \n" + "QR Code to scan",
                color = Color.White, fontSize = 13, fontWeight = FontWeight.W500,
            )
            Box{
                CameraPreview(
                    modifier = Modifier,
                    accountDetails = { details ->
                        try {
                            if (
                                Pattern.matches(".*30000.*", details) || Pattern.matches(".*101000.*", details)
                            ) {
//                                kegowViewModel.setQrCodeData(details)
//                                navigator.navToQRCodeTransfer()
                            } else {
                                navigator.navigateBack()
                                context.displayToastMessage("Invalid QR Code. Kindly scan a Runteller QR Code")
                            }
                        } catch (e: Exception) {
//                            navigator.navToKegowApp()
                        } finally {
//                            navigator.navToKegowApp()
                        }
                    },
                    onFailed = {
//                        context.displayToastMessage("Please scan a valid Kegow QR Code")
//                        navigator.navToKegowApp()
                    },
                    navigator = navigator
                )

                GIFImage(gifImage = R.drawable.scan, modifier = Modifier.size(400.dp))
            }

            Text(
                text = buildAnnotatedString {
                    append("Powered by ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W500, color = Color.Green, fontSize = 14.sp
                    )) {
                        append("Runteller ")
                    }
                    append("with \u2764")
                },
                color = Color.White, fontWeight = FontWeight.W500, fontSize = 12.sp,
                lineHeight = 2.sp
            )
        }
    }
}

