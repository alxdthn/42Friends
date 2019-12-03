package com.nalexand.friendlocation.model.local

import androidx.room.Entity

@Entity
data class Note(
	val id: Int,
	val user_id: String,
	val note: String,
	val header: String,
	val date: Long
)