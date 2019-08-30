package com.example.getfriendlocation.network

data class TokenResponse(
    val access_token: String,
    val created_at: Int,
    val expires_in: Int,
    val scope: String,
    val token_type: String
)

data class TokenRequest(
    val grant_type: String,
    val client_id: String,
    val client_secret: String
)

data class User(
    val id: Int,
    val login: String,
    val url: String
)

data class UserLocation(
    val begin_at: String?,
    val campus_id: Int,
    val end_at: String?,
    val floor: String?,
    val host: String?,
    val id: Int,
    val post: String?,
    val primary: Boolean,
    val row: String?,
    val user: User
)
