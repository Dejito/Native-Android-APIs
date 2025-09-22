package com.mobile.nativeandroidapis.router

sealed class Routes(val route: String) {

    data object Home : Routes("/home")
    data object Bluetooth : Routes("/bluetooth")
    data object QRCodeScanner : Routes("/qr-code-scanner")
    data object QRCodeView : Routes("/qr-code-view")
    data object QRCodeOptionScreen : Routes("/qr-code-options")
    data object DisplayScannedQRScreen : Routes("/display-scanned-qr-screen")



}
