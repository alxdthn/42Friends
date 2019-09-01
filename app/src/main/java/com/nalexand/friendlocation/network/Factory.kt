package com.nalexand.friendlocation.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    fun makeClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.followRedirects(true)
        client.addNetworkInterceptor { chain ->
            val response = chain.proceed(chain.request())
            response
        }
        return client.build()
    }

    fun makeService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl("https://api.intra.42.fr/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }
}