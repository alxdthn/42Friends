package com.nalexand.friendlocation.repository.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.nalexand.friendlocation.base.BaseDao
import com.nalexand.friendlocation.model.entity.NoteEntity
import io.reactivex.Single

@Dao
interface NoteDao : BaseDao<NoteEntity> {

	@Query("SELECT * FROM NoteEntity WHERE user_id LIKE (:user_id) ORDER BY Date ASC")
	fun getAllByUser(user_id: String) : Single<List<NoteEntity>>
}