package com.mobile.nativeandroidapis.bluetooth.presentation.view

import android.content.Context
import android.widget.Toast

fun Context.displayToastMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
