package com.nalexand.friendlocation.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
	@PrimaryKey(autoGenerate = true)
	val id: Int,
	@ColumnInfo(name = "user_id")
	val user_id: String,
	val note: String,
	val header: String,
	val date: Long
)