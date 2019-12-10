package com.nalexand.friendlocation.main

import android.app.Application
import androidx.core.content.ContextCompat
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.di.component.AppComponent
import com.nalexand.friendlocation.di.component.DaggerAppComponent
import com.nalexand.friendlocation.utils.UserBinder

class App : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        UserBinder.init(
            ContextCompat.getColor(applicationContext, R.color.enabled_color),
            ContextCompat.getColor(applicationContext, R.color.disabled_color),
            10f
        )

        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}