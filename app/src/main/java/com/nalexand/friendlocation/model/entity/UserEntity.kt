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
	var host: String? = null,
	var begin_at: String? = null,
	var end_at: String? = null
)