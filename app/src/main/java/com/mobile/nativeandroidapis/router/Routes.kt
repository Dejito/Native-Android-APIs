package com.mobile.petra.router

sealed class Routes(val route: String) {

    data object Home : Routes("home")
    data object SignIn : Routes("/sign-in")



}
