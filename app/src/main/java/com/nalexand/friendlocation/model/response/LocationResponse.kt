package com.nalexand.friendlocation.model.response

data class LocationResponse(
	val end_at: String?,
	val id: Int,
	val begin_at: String?,
	val primary: Boolean,
	val host: String?,
	val campus_id: Int,
	val user: UserResponse
)