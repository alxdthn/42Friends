package com.nalexand.friendlocation.data

import androidx.room.*
import com.nalexand.friendlocation.model.local.Member
import com.nalexand.friendlocation.model.local.Note

@Dao
interface AppDao {
	@Query("SELECT * FROM Member ORDER BY end_at ASC")
	fun getAllMembers(): MutableList<Member>

	@Query("SELECT * FROM Member WHERE user_id LIKE (:user_id)")
	fun getById(user_id: Long): Member

	@Query("SELECT * FROM Member WHERE login LIKE :login")
	fun getByLogin(login: String): Member

	@Query("SELECT COUNT(login) FROM Member")
	fun getCount() : Int

	@Query("SELECT * FROM Note WHERE user_id LIKE (:user_id) ORDER BY Date ASC")
	fun getNotes(user_id: String) : MutableList<Note>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(member: Member)

	@Insert
	fun insertNote(note: Note)

	@Update
	fun updateNote(note: Note)

	@Delete
	fun deleteNote(note: Note)

	@Update
	fun update(member: Member)

	@Delete
	fun delete(member: Member)
}