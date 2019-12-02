package com.nalexand.friendlocation.model.local

data class Location(
	val end_at: String?,
	val id: Int,
	val begin_at: String?,
	val primary: Boolean,
	val host: String?,
	val campus_id: Int,
	val member: Member
)