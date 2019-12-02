package com.nalexand.friendlocation.data

import androidx.room.*
import com.nalexand.friendlocation.model.entity.MemberEntity
import com.nalexand.friendlocation.model.entity.NoteEntity

@Dao
interface AppDao {
	@Query("SELECT * FROM MemberEntity ORDER BY end_at ASC")
	fun getAll(): MutableList<MemberEntity>

	@Query("SELECT * FROM MemberEntity WHERE user_id LIKE (:user_id)")
	fun getById(user_id: Long): MemberEntity

	@Query("SELECT * FROM MemberEntity WHERE login LIKE :login")
	fun getByLogin(login: String): MemberEntity

	@Query("SELECT COUNT(login) FROM memberEntity")
	fun getCount() : Int

	@Query("SELECT * FROM NoteEntity WHERE user_id LIKE (:user_id) ORDER BY Date ASC")
	fun getNotes(user_id: String) : MutableList<NoteEntity>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(memberEntity: MemberEntity)

	@Insert
	fun insertNote(note: NoteEntity)

	@Update
	fun updateNote(note: NoteEntity)

	@Delete
	fun deleteNote(note: NoteEntity)

	@Update
	fun update(memberEntity: MemberEntity)

	@Delete
	fun delete(memberEntity: MemberEntity)
}