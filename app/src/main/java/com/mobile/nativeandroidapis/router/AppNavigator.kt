package com.mobile.nativeandroidapis.router

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobile.petra.router.Navigator
import com.mobile.petra.router.Routes


@SuppressLint("NewApi")
@Composable
fun AppNavigators() {
//    val appInBackground = kegowViewModel.isAppInBackground.collectAsState().value
    val navController = rememberNavController()
    val navigation = Navigator(navController)


    NavHost(navController, startDestination = Routes.Home.route) {

        composable(Routes.Home.route) {
//            LoginScreen(navigation)
        }

    }
}


//@Composable
//fun DashboardWithDelay(
//    navigation: Navigator,
//    kegowViewModel: KegowViewModel,
//
//    ) {
//    val context = LocalContext.current
//    val sharedPreferencesManager = AndroidSharedPreferencesManager(context)
//    val navigateToOnboarding = remember { mutableStateOf(false) }
//    val hideOnboardingScreens = sharedPreferencesManager.getBoolean("hide_onboarding_screen")
//    LaunchedEffect(Unit) {
//        delay(4000)
//        navigateToOnboarding.value = true
//    }
//
//    if (navigateToOnboarding.value) {
//        if (!hideOnboardingScreens)
//            SwipeScreen(navigator = navigation)
//        else
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                Login(navigator = navigation, kegowViewModel = kegowViewModel)
//            }
//    } else {
//        LauncherScreen()
//    }
//
//}