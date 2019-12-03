package com.nalexand.friendlocation.di.modules

import android.app.Application
import com.nalexand.friendlocation.repository.app.AppPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object StorageModule {

    @Provides
    @Singleton
    internal fun provideSharedPreferences(application: Application): AppPreferences {
        return AppPreferences(
			application
		)
    }
}