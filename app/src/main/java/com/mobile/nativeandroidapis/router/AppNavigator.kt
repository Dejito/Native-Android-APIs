package com.mobile.nativeandroidapis.router

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobile.nativeandroidapis.bluetooth.presentation.view.BluetoothHomeScreen
import com.mobile.nativeandroidapis.ui.screens.AppHomepage


@SuppressLint("NewApi")
@Composable
fun AppNavigators() {

    val navController = rememberNavController()
    val navigation = Navigator(navController)

    NavHost(navController, startDestination = Routes.Home.route) {

        composable(Routes.Home.route) {
            AppHomepage(navigation)
        }
        composable(Routes.Bluetooth.route) {
            BluetoothHomeScreen(onNavUp = {navigation.goBack()})
        }

    }
}
