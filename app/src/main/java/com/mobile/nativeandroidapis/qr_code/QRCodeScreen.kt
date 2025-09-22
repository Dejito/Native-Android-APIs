package com.mobile.nativeandroidapis.qr_code

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lightspark.composeqr.QrCodeView
import com.mobile.nativeandroidapis.router.Navigator
import com.mobile.nativeandroidapis.ui.screens.CustomAppBar
import com.mobile.nativeandroidapis.R
import com.mobile.nativeandroidapis.ui.screens.TitleText

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun QRCodeScreen(navigator: Navigator,) {


    Scaffold(
        topBar = {
                CustomAppBar(
                    title = "My QR Code",
                    onClick = { navigator.goBack() },
                )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
                .padding(start = 26.dp, end = 26.dp, top = 26.dp, bottom = 0.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .background(color = Color(0xFFE6F2ED), RoundedCornerShape(2))
                        .padding(top = 10.dp, start = 15.dp, end = 15.dp, bottom = 10.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TitleText(
                        text = "Use Kegow Scan to pay me",
                        color = Color(0xFF686868),
                        fontSize = 12
                    )

                    Box(
                        modifier = Modifier
                            .padding(vertical = 6.dp, horizontal = 12.dp)
                            .background(Color.White, RoundedCornerShape(4))
                    ) {
                        val encryptedQRData =
                            ""
                        QrCodeView(
                            data = encryptedQRData,
                            modifier = Modifier
                                .height(200.dp)
                                .width(200.dp)
                                .padding(14.dp)
//                            .background(Color.White)
                                .fillMaxWidth(),
                            overlayContent = {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .border(1.dp, Color.White, CircleShape)
                                        .background(Color.White, CircleShape),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.runteller),
                                        contentScale = ContentScale.Fit,
                                        contentDescription = "Kegow logo",
                                        modifier = Modifier
                                            .size(150.dp)
                                            .padding(3.dp)
                                    )
                                }
                            }
                        )
                    }
                    TitleText(
                        text = "",
                        color = Color(0xFF1E312D),
                        fontWeight = FontWeight.Bold,
                        topPadding = 5, fontSize = 15
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TitleText(
                    text = "Powered by",
                    fontSize = 12,
                    bottomPadding = 0,
                    color = Color(0xFF1E312D),
                    textAlign = TextAlign.Center,
                )
                Image(
                    painter = painterResource(id = R.drawable.runteller),
                    contentDescription = "run teller logo",
                    modifier = Modifier.size(80.dp)
                )
            }


        }
    }
}