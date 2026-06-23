package com.garageinventory

import android.app.Application
import com.garageinventory.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GarageApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GarageApplication)
            modules(appModule)
        }
    }
}