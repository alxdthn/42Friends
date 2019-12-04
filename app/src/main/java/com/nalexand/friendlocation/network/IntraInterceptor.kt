package com.nalexand.friendlocation.network

import com.nalexand.friendlocation.repository.app.AppPreferences
import com.nalexand.friendlocation.utils.Constants.CLIENT_ID
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class IntraInterceptor @Inject constructor(
    private val preferences: AppPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
        val token = preferences.getToken()

        newRequest.addHeader("Authorization", "Bearer ${token.value}")
        return chain.proceed(newRequest.build())
    }
}