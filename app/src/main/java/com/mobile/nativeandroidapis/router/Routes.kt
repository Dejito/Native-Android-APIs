package com.mobile.nativeandroidapis.router

sealed class Routes(val route: String) {

    data object Home : Routes("/home")
    data object Bluetooth : Routes("/bluetooth")
    data object QRCode : Routes("/qr-code")
//    data object Bluetooth : Routes("/bluetooth")



}
