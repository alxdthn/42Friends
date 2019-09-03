package com.nalexand.friendlocation.data

import androidx.room.*

@Dao
interface AppDao {
	@Query("SELECT * FROM UserEntity ORDER BY end_at DESC")
	fun getAll(): MutableList<UserEntity>

	@Query("SELECT * FROM UserEntity WHERE user_id LIKE (:user_id)")
	fun getById(user_id: Long): UserEntity

	@Query("SELECT * FROM UserEntity WHERE login LIKE :login")
	fun getByLogin(login: String): UserEntity

	@Query("SELECT * FROM Token")
	fun getToken(): Token

	@Query("SELECT COUNT(login) FROM UserEntity")
	fun getCount() : Int

	@Query("SELECT * FROM Note WHERE user_id LIKE (:user_id) ORDER BY Date ASC")
	fun getNotes(user_id: String) : MutableList<Note>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(userEntity: UserEntity)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertToken(token: Token)

	@Insert
	fun insertNote(note: Note)

	@Update
	fun updateNote(note: Note)

	@Delete
	fun deleteNote(note: Note)

	@Delete
	fun delteAllNotes(notes: MutableList<Note>)

	@Update
	fun update(userEntity: UserEntity)

	@Delete
	fun delete(userEntity: UserEntity)
}