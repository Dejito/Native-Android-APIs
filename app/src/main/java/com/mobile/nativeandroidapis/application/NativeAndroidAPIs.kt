package com.mobile.nativeandroidapis.application

import android.app.Application
import com.mobile.nativeandroidapis.di.initKoin
import org.koin.android.ext.koin.androidContext


class NativeAndroidAPIs : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@NativeAndroidAPIs)
//            AndroidLogger(Level.ALL)
        }
    }
}