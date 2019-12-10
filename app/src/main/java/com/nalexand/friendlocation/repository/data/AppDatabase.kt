package com.nalexand.friendlocation.repository.data

import android.app.Application
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nalexand.friendlocation.model.entity.NoteEntity
import com.nalexand.friendlocation.model.entity.UserEntity
import com.nalexand.friendlocation.model.local.User
import com.nalexand.friendlocation.model.local.Note
import com.nalexand.friendlocation.repository.data.dao.NoteDao
import com.nalexand.friendlocation.repository.data.dao.UserDao

@Database(
	entities = [UserEntity::class, NoteEntity::class],
	version = 1,
	exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

	abstract fun userDao(): UserDao

	abstract fun noteDao(): NoteDao

	companion object {
		@Volatile private var instance: AppDatabase? = null
		private val LOCK = Any()

		operator fun invoke(application: Application) = instance
			?: synchronized(LOCK) {
			instance
				?: buildDatabase(
					application
				).also { instance = it }
		}

		private fun buildDatabase(application: Application): AppDatabase {
			return Room.databaseBuilder(application,
					AppDatabase::class.java,
					"friendlocation.db")
					.allowMainThreadQueries().build()
		}
	}
}
