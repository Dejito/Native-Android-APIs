package com.mobile.nativeandroidapis.ui.screens

import android.content.Context
import android.widget.Toast

fun Context.displayToastMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
