package com.nalexand.friendlocation.model.local

data class User(
	val id: String,
	val login: String,
	var host: String? = null,
	var begin_at: String? = null,
	var end_at: String? = null,
	var name: String? = null
)