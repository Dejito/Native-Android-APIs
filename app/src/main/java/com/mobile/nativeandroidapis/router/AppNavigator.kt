package com.mobile.nativeandroidapis.router

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobile.nativeandroidapis.ui.screens.AppHomepage
import com.mobile.petra.router.Navigator
import com.mobile.petra.router.Routes


@SuppressLint("NewApi")
@Composable
fun AppNavigators() {

    val navController = rememberNavController()
    val navigation = Navigator(navController)


    NavHost(navController, startDestination = Routes.Home.route) {

        composable(Routes.Home.route) {
            AppHomepage(navigation)
        }

    }
}
