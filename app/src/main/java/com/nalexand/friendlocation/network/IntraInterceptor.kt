package com.nalexand.friendlocation.network

import com.nalexand.friendlocation.repository.AppPreferences
import com.nalexand.friendlocation.utils.Constants.CLIENT_ID
import com.nalexand.friendlocation.utils.Constants.TOKEN_PREF_KEY
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class IntraInterceptor @Inject constructor(
    private val preferences: AppPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
        val token = preferences.getString(TOKEN_PREF_KEY)
        val url = originalRequest.url().newBuilder()
            .addQueryParameter("token", token)
            .addQueryParameter("key", CLIENT_ID)
            .build()

        newRequest.url(url)
        return chain.proceed(newRequest.build())
    }
}