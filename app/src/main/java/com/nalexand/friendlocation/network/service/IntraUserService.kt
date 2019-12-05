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

    @GET("/v2/users")
    fun getUser(
        @Query("filter[login]") login: String
    ): Single<Array<UserResponse>>

    @GET("/v2/locations")
    fun getUserLocation(
        @Header("Authorization") auth: String?,
        @Query("filter[user_id]", encoded = true) user_id: String,
        @Query("filter[end]", encoded = true) end: String
    ): Single<Array<LocationResponse>>
}