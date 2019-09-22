package com.nalexand.friendlocationv2.data

import androidx.room.*

@Dao
interface AppDao {
	@Query("SELECT * FROM UserEntity ORDER BY end_at ASC")
	fun getAll(): MutableList<UserEntity>

	@Query("SELECT * FROM UserEntity WHERE user_id LIKE (:user_id)")
	fun getById(user_id: Long): UserEntity

	@Query("SELECT * FROM UserEntity WHERE login LIKE :login")
	fun getByLogin(login: String): UserEntity

	@Query("SELECT COUNT(login) FROM UserEntity")
	fun getCount() : Int

	@Query("SELECT * FROM Note WHERE user_id LIKE (:user_id) ORDER BY Date ASC")
	fun getNotes(user_id: String) : MutableList<Note>

	@Query("SELECT login FROM UserEntity")
	fun getAllLogins() : Array<String>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(userEntity: UserEntity)

	@Insert
	fun insertNote(note: Note)

	@Update
	fun updateNote(note: Note)

	@Delete
	fun deleteNote(note: Note)

	@Delete
	fun deleteAllNotes(notes: MutableList<Note>)

	@Update
	fun update(userEntity: UserEntity)

	@Delete
	fun delete(userEntity: UserEntity)
}