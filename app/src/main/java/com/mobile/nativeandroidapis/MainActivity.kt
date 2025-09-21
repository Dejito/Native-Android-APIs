package com.mobile.nativeandroidapis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mobile.nativeandroidapis.bluetooth.presentation.view.BluetoothHomePage
import com.mobile.nativeandroidapis.ui.theme.NativeAndroidAPIsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NativeAndroidAPIsTheme {
                BluetoothHomePage()
            }
        }
    }
}
