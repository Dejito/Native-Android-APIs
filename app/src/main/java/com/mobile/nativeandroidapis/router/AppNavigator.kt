package com.mobile.nativeandroidapis.router

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobile.nativeandroidapis.bluetooth.presentation.view.BluetoothHomeScreen
import com.mobile.nativeandroidapis.nfc.presentation.view.NfcScreen
import com.mobile.nativeandroidapis.qr_code.view.DisplayScannedQRScreen
import com.mobile.nativeandroidapis.qr_code.view.QRCodeScannerScreen
import com.mobile.nativeandroidapis.qr_code.view.QRCodeScreen
import com.mobile.nativeandroidapis.qr_code.view.SelectQRCodeOption
import com.mobile.nativeandroidapis.ui.screens.AppHomepage


@SuppressLint("NewApi")
@Composable
fun AppNavigator() {

    val navController = rememberNavController()
    val navigation = Navigator(navController)

    NavHost(navController, startDestination = Routes.Home.route) {

        composable(Routes.Home.route) {
            AppHomepage(navigation)
        }
        composable(Routes.Bluetooth.route) {
            BluetoothHomeScreen( navigation)
        }
        composable(Routes.QRCodeScanner.route) {
            QRCodeScannerScreen(navigation)
        }
        composable(Routes.QRCodeView.route) {
            QRCodeScreen(navigation)
        }
        composable(Routes.QRCodeOptionScreen.route) {
            SelectQRCodeOption(navigation)
        }
        composable(Routes.DisplayScannedQRScreen.route) {
            DisplayScannedQRScreen(navigation)
        }
        composable(Routes.NFCScreen.route) {
            NfcScreen()
        }

    }
}
