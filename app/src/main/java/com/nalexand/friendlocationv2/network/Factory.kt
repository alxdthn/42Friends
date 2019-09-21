package com.nalexand.friendlocationv2.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    fun makeService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl("https://api.intra.42.fr/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }
}