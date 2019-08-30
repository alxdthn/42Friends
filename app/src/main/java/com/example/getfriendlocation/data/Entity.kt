package com.example.getfriendlocation.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserLocationEntity(
	@PrimaryKey
    @ColumnInfo(name = "user_id")
	val user_id: Int,
    @ColumnInfo(name = "login")
    val login: String,
	val url: String,
	val begin_at: String? = null,
	val campus_id: Int = 0,
	val end_at: String? = null,
	val floor: String? = null,
	val host: String? = null,
	val id: Int = 0,
	val post: String? = null,
	val primary: Boolean = false,
	val row: String? = null
)