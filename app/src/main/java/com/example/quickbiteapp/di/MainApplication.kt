package com.example.quickbiteapp.di

import android.app.Application

class MainApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .appModule(AppModule)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
    }
}