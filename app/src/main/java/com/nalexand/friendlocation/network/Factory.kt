package com.nalexand.friendlocation.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    fun makeService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl("https://api.intra.42.fr/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }
}