package com.nalexand.friendlocation.network.service

import com.nalexand.friendlocation.model.response.LocationResponse
import com.nalexand.friendlocation.model.response.TokenResponse
import com.nalexand.friendlocation.model.response.UserResponse
import com.nalexand.friendlocation.utils.Constants.CLIENT_ID
import com.nalexand.friendlocation.utils.Constants.CLIENT_SECRET
import com.nalexand.friendlocation.utils.Constants.GRAND_TYPE
import io.reactivex.Single
import retrofit2.http.*

interface IntraUserService {

    @POST("oauth/token")
    fun refreshToken(
        @Query("grant_type") grantType: String = GRAND_TYPE,
        @Query("client_id") clientId: String = CLIENT_ID,
        @Query("client_secret") clientSecret: String = CLIENT_SECRET
    ): TokenResponse

    @GET("/v2/users/?")
    fun getUser(
        @Query("filter[login]", encoded = true) login: String
    ): Single<Array<UserResponse>>

    @GET("/v2/locations")
    fun getUserLocation(
        @Header("Authorization") auth: String?,
        @Query("filter[user_id]", encoded = true) user_id: String,
        @Query("filter[end]", encoded = true) end: String
    ): Single<Array<LocationResponse>>
}