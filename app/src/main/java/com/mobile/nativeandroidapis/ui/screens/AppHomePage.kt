package com.mobile.nativeandroidapis.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.nativeandroidapis.bluetooth.presentation.viewmodel.BluetoothViewModel
import com.mobile.nativeandroidapis.utils.DisabledBackPress
import com.mobile.nativeandroidapis.router.Navigator
import org.koin.androidx.compose.koinViewModel


@Composable
fun AppHomepage(navigator: Navigator, bluetoothViewModel: BluetoothViewModel = koinViewModel()) {
    Scaffold(
        topBar = { CustomAppBar(title = "Homepage", enabledBackButton = false) }
    ) { paddingValues ->

        DisabledBackPress()

        val context = LocalContext.current

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            AppHomePageCard(title = "Bluetooth",
                onClick = {
                    bluetoothViewModel.setDefaultUiState()
                    navigator.navToBluetooth()
                }
            )
            AppHomePageCard(title = "QRCode", onClick = { navigator.navToQROptionScreen() })
            AppHomePageCard(title = "NFC", onClick = {
                navigator.navToNFCScreen()
            })
            AppHomePageCard(title = "SQLite Cypher", onClick = {
                navigator.navToSQLCipher()
            })
        }
    }
}


@Composable
internal fun AppHomePageCard(
    title: String, fontSize: Int = 15,
    onClick: () -> Unit = {}
) {
    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .padding(top = 24.dp)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontSize = fontSize.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(bottom = 3.dp)
            )

            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .size(58.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = ("forward nav icon"),
                )
            }
        }
    }
}