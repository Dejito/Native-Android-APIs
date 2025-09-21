package com.mobile.petra.router

sealed class Routes(val route: String) {

    data object Home : Routes("/home")
    data object Bluetooth : Routes("/bluetooth")
    data object QRCode : Routes("/bluetooth")
//    data object Bluetooth : Routes("/bluetooth")



}
