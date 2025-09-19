package com.mobile.petra.router

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobile.petra.presentation.views.auth.sign_in.LoginScreen
import com.mobile.petra.presentation.views.auth.sign_up.SignUpScreen
import com.mobile.petra.presentation.views.products.screens.ProductDetailScreen
import com.mobile.petra.presentation.views.products.screens.ProductListingScreen

@SuppressLint("NewApi")
@Composable
fun AppNavigators() {
//    val appInBackground = kegowViewModel.isAppInBackground.collectAsState().value
    val navController = rememberNavController()
    val navigation = Navigator(navController)


    NavHost(navController, startDestination = Routes.Home.route) {

        composable(Routes.Home.route) {
            LoginScreen(navigation)
        }

        composable(Routes.SignIn.route) {
            LoginScreen(navigation)
        }

        composable(Routes.SignUp.route) {
            SignUpScreen(navigation)
        }

        composable(Routes.ProductsListing.route) {
            ProductListingScreen(navigation)
        }

        composable(Routes.ProductDetail.route) {
            ProductDetailScreen(navigation)
        }


//        if (appInBackground == 1) {
//            navigation.navToWelcomeScreen()
//            kegowViewModel.setBackground(0)
//        } else if (appInBackground == 2) {
//            navigation.navToLoginScreen()
//            kegowViewModel.setBackground(0)
//        }

//        composable(Routes.WelcomeScreen.route) {
//            WelcomeBack(navigation, kegowViewModel = kegowViewModel)
//        }
//        composable(Routes.Home.route) {
//            DashboardWithDelay(navigation = navigation, kegowViewModel)
//        }


//        composable(Routes.EnterPhoneNumber.route,
//            deepLinks = listOf(
//                navDeepLink {
//                    uriPattern = if(isDebugConstant){"https://staging-dot-kegow-315917.nw.r.appspot.com/referral/{id}"}else{"https://app.kegow.com/signup/{id}"}
//                    // uriPattern = "https://app.kegow.com/{id}"
//                    action = Intent.ACTION_VIEW
//                }
//            ),
//            arguments = listOf(
//                navArgument(name = "id") {
//                    type = NavType.StringType
//                    defaultValue = ""
//                }
//            )
//        ) { entry ->
//
//            val referral = entry.arguments?.getString("id") ?: ""
//            InputPhoneNumber(
//                navigator = navigation,
//                kegowViewModel = kegowViewModel,
//                referral =  referral
//            )
//
//        }


//        composable(
//            Routes.TransactionsHistory.route + "/{walletNumber}",
//            arguments = listOf(navArgument("walletNumber") {
//                type = NavType.StringType
//            })
//        ) { args ->
//            val state = args.arguments?.getString("walletNumber")
//            TransactionsHistory(
//                navigation = navigation,
//                kegowViewModel = kegowViewModel,
//                walletNumber = state,
//
//                )
//        }


//        composable(Routes.DeactivateCard.route) {
//            DeactivateCard(
//                onNavigateUp = { navController.navigateUp() },
//                onOkayClicked = {
//                    navController.navigate(Routes.KegowApp.route) {
//                        navController.popBackStack(
//                            BottomNavigationScreen.Card.route,
//                            inclusive = true
//                        )
//                    }
//                }
//            )
//        }

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