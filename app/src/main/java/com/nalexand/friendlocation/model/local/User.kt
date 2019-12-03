package com.nalexand.friendlocation.model.local

data class User(
	val id: String,
	val login: String,
	val host: String? = null,
	val begin_at: String? = null,
	val end_at: String? = null
)