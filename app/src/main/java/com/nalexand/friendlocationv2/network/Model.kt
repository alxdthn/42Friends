package com.nalexand.friendlocationv2.network

data class User(
    val user_id: String,
    val login: String,
    val begin_at: String?,
    val end_at: String?,
    val host: String?,
    val error: Int
)