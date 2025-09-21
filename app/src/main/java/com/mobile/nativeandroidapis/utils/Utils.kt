package com.mobile.nativeandroidapis.utils

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun DisabledBackPress() {
    val lifecycleOwner = LocalLifecycleOwner.current
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    DisposableEffect(lifecycleOwner, onBackPressedDispatcher) {
        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle the back button press here if needed
            }
        }

        onBackPressedDispatcher?.addCallback(lifecycleOwner, backCallback)

        onDispose {
            backCallback.remove()
        }
    }

}
