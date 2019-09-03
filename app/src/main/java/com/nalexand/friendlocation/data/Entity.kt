package com.nalexand.friendlocation.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
	@PrimaryKey
    @ColumnInfo(name = "user_id")
	val user_id: String,
    @ColumnInfo(name = "login")
    val login: String,
	val host: String? = null,
	val begin_at: String? = null,
	val end_at: String? = null
//	val notes: MutableList<String>? = null
)

@Entity
data class Token(
	@PrimaryKey
	val id: Int,
	val value: String,
	val type: String,
	val createdAt: Long
)

@Entity
data class Note(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	@ColumnInfo(name = "user_id")
	val user_id: String,
	val note: String,
	val header: String,
	val date: Long
)