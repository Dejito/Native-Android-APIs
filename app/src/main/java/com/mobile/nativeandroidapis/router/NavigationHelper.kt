package com.mobile.nativeandroidapis.router

import androidx.navigation.NavHostController


class Navigator(private val navController: NavHostController) {


    fun navToBluetooth(){
        navController.navigate(Routes.Bluetooth.route)
    }

    fun navToQRCodeScanner(){
        navController.navigate(Routes.QRCodeScanner.route)
    }

    fun navToQRCodeScreen(){
        navController.navigate(Routes.QRCodeView.route)
    }

    fun navToQROptionScreen(){
        navController.navigate(Routes.QRCodeOptionScreen.route)
    }

    fun navToDisplayScannedQRScreen(){
        navController.navigate(Routes.DisplayScannedQRScreen.route)
    }

    fun navigateBack() {
        navController.navigateUp()
    }

}
