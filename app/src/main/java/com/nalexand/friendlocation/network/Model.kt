package com.nalexand.friendlocation.network

data class TokenResponse(
    val access_token: String,
    val created_at: Long,
    val expires_in: Int,
    val scope: String,
    val token_type: String
)

data class TokenRequest(
    val grant_type : String = "client_credentials",
    val client_id: String = "6d94ec75f2839e414dcba355566192b67ee05bc2d9a3da66aee8645343a4bffd",
    val client_secret: String = "de6ac060116057e0d3129a461d45ace116c3136da2b2e8778db39ab4fd6b53bd"
)

data class User(
    val id: String,
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
