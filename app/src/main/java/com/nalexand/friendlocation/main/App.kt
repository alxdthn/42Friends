package com.nalexand.friendlocation.main

import android.app.Application
import com.nalexand.friendlocation.di.component.AppComponent
import com.nalexand.friendlocation.di.component.DaggerAppComponent

class App : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}