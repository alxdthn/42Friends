package com.nalexand.friendlocation.network.service

import com.nalexand.friendlocation.model.response.LocationResponse
import com.nalexand.friendlocation.model.response.TokenResponse
import com.nalexand.friendlocation.model.response.UserResponse
import io.reactivex.Single
import retrofit2.http.*

interface IntraUserService {

    @GET("/v2/users")
    fun getUser(
        @Query("filter[login]") login: String
    ): Single<Array<UserResponse>>

    @GET("/v2/locations")
    fun getUserActiveLocation(
        @Query("filter[user_id]") user_id: String,
        @Query("filter[end]") end: String = "false",
        @Query("page[size]") pageSize: Int = 100
    ): Single<Array<LocationResponse>>

    @GET("/v2/locations")
    fun getUserLastLocation(
        @Query("filter[user_id]") user_id: String,
        @Query("per_page") per_page: Int = 1
    ): Single<Array<LocationResponse>>
}