package com.journal

import android.app.Application
import com.google.android.material.color.DynamicColors
import com.journal.di.appModule
import com.journal.di.dbModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)

        startKoin() {
            androidContext(this@MainApplication)
            androidLogger()
            modules(appModule, dbModule)
        }
    }
}