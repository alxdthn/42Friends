package com.nalexand.friendlocation.di.modules

import android.app.Application
import com.nalexand.friendlocation.repository.data.AppDatabase
import com.nalexand.friendlocation.repository.data.dao.NoteDao
import com.nalexand.friendlocation.repository.data.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataBaseModule {

	@Provides
	@Singleton
	internal fun provideUserDao(application: Application) : UserDao {
		return AppDatabase.invoke(application).userDao()
	}

	@Provides
	@Singleton
	internal fun provideNoteDao(application: Application) : NoteDao {
		return AppDatabase.invoke(application).noteDao()
	}
}