package com.nalexand.friendlocation.di.modules

import android.app.Application
import androidx.room.RoomDatabase
import com.nalexand.friendlocation.data.AppDatabase
import dagger.Provides
import javax.inject.Singleton

object DataBaseModule {

	@Provides
	@Singleton
	internal fun provideDataBase(application: Application) : RoomDatabase {
		return AppDatabase.invoke(application)
	}
}