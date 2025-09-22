package com.mobile.nativeandroidapis.router

import androidx.navigation.NavHostController


class Navigator(private val navController: NavHostController) {


    fun navToBluetooth(){
        navController.navigate(Routes.Bluetooth.route)
    }

    fun navigateBack() {
        navController.navigateUp()
    }

}
