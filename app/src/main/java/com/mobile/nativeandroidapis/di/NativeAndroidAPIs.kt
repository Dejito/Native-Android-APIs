package com.mobile.nativeandroidapis.di

import android.app.Application
import org.koin.android.ext.koin.androidContext

class NativeAndroidAPIs : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@NativeAndroidAPIs)
        }
    }
}