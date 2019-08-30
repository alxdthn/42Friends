package com.example.getfriendlocation.network

import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {

    @POST("oauth/token")
    suspend fun getToken(
        @Body requestBody: TokenRequest): Response<TokenResponse>

    @GET("/v2/users/?")
    suspend fun getUser(
        @Header("Authorization") auth: String?,
        @Query("filter[login]", encoded = true) login: String): Response<Array<User>>

    @GET("/v2/locations/?")
    suspend fun getUserLocation(
        @Header("Authorization") auth: String?,
        @Query("filter[user_id]", encoded = true) user_id: Int): Response<Array<UserLocation>>

}