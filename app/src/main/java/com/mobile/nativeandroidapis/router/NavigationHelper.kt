package com.mobile.petra.router

import androidx.navigation.NavHostController


class Navigator(private val navController: NavHostController) {

//    fun navigateToHome() {
//        navController.navigate(Routes.Home.route) {
//            launchSingleTop = true
//            restoreState = true
//            popUpTo(Routes.Home.route) {
//                inclusive = false
//            }
//        }
//    }

    fun navToSignIn() {
        navController.navigate(Routes.SignIn.route)
    }
    fun navToSignUp() {
        navController.navigate(Routes.SignUp.route)
    }
    fun navigateUp() {
        navController.navigateUp()
    }
    fun navToProductsListing() {
        navController.navigate(Routes.ProductsListing.route)
    }
    fun navToProductDetail() {
        navController.navigate(Routes.ProductDetail.route)
    }

//
//    fun navToLoginScreen() {
//        navController.navigate(Routes.Login.route) {
//            popUpTo(Routes.ResetLoginPinSuccessful.route){
//                inclusive = true
//            }
//        }
//    }
//
//    fun navToTransferSuccessfulScreen() {
//        navController.navigate(Routes.TransferSuccessful.route) {
//            popUpTo(Routes.BankTransfer.route) {
//                inclusive = true
//            }
//            popUpTo(Routes.KegowTransfer.route) {
//                inclusive = true
//            }
//        }
//    }
//
//    fun navToTierUpgradeSelection() {
//        navController.navigate(Routes.TierUpgradeSelection.route) {
//            popUpTo(Routes.TierUpgradeSelection.route) {
//                this@navigate.launchSingleTop = true
//            }
//        }
//    }
//
//    fun goBack() {
//        navController.navigateUp()
//    }
//
//


}
