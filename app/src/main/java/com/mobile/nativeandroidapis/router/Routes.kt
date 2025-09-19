package com.mobile.petra.router

sealed class Routes(val route: String) {

    data object Home : Routes("home")
    data object SignIn : Routes("/sign-in")
    data object SignUp : Routes("/sign-up")
    data object ProductsListing : Routes("/products-listing")
    data object ProductDetail : Routes("/product-details")


}
