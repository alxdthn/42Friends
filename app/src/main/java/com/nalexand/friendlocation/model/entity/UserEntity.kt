package com.nalexand.friendlocation.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
	@PrimaryKey
	@ColumnInfo(name = "id")
	val id: String,
	@ColumnInfo(name = "login")
	val login: String,
	val host: String? = null,
	val begin_at: String? = null,
	val end_at: String? = null
)