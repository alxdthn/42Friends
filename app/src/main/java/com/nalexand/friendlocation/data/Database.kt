package com.nalexand.friendlocation.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
	entities = [UserEntity::class, Token::class, Note::class],
	version = 1,
	exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
	abstract fun make(): AppDao

	companion object {
		@Volatile private var instance: AppDatabase? = null
		private val LOCK = Any()

		operator fun invoke(application: Application) = instance
			?: synchronized(LOCK){
			instance
				?: buildDatabase(application).also { instance = it }
		}

		private fun buildDatabase(application: Application): AppDatabase {
			Log.d("bestTAG", "building db")
			return Room.databaseBuilder(application,
					AppDatabase::class.java,
					"friendlocation.db")
					.allowMainThreadQueries().build()
		}
	}
}
