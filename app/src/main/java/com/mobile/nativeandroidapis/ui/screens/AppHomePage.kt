package com.mobile.nativeandroidapis.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.petra.router.Navigator


@Composable
fun AppHomepage(navigator: Navigator) {
    Scaffold(
        topBar = {}
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            AppHomePageCard(title = "Bluetooth", onClick = {})
            AppHomePageCard(title = "QRCode", onClick = {})
            AppHomePageCard(title = "SQLite Cypher", onClick = {})
            AppHomePageCard(title = "NFC", onClick = {})
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
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .paddingFromBaseline(bottom = 20.dp)
                .clickable { onClick() }
        ) {
            Spacer(modifier = Modifier.weight(0.001f))

            Text(
                text = title,
                fontSize = fontSize.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(bottom = 3.dp)
            )

            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .weight(0.2f)
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