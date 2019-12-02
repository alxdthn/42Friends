package com.nalexand.friendlocation.model.response

data class TokenResponse(
	val access_token: String,
	val created_at: Long,
	val expires_in: Int,
	val scope: String,
	val token_type: String
)