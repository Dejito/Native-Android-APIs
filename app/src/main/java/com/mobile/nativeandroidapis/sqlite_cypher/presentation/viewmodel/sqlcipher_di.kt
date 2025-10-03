package com.mobile.nativeandroidapis.sqlite_cypher.presentation.viewmodel

import com.mobile.nativeandroidapis.sqlite_cypher.data.AppDatabase
import org.koin.dsl.module

val sqlCipherModule = module {

    single {
        AppDatabase.getInstance(get())
    }

    single {
        get<AppDatabase>().getFakeDao()
    }

    single {
        SqlCipherViewModel(get())
    }
}
