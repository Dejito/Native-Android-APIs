package com.mobile.nativeandroidapis.router

sealed class Routes(val route: String) {

    data object Home : Routes("/home")
    data object Bluetooth : Routes("/bluetooth")
    data object QRCodeScanner : Routes("/qr-code-scanner")
    data object QRCodeView : Routes("/qr-code-view")



}
