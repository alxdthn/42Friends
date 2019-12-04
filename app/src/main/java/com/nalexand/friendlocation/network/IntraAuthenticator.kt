package com.nalexand.friendlocation.network

import com.nalexand.friendlocation.network.service.IntraAuthService
import com.nalexand.friendlocation.repository.app.AppPreferences
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.lang.IllegalStateException
import javax.inject.Inject

class IntraAuthenticator @Inject constructor(
	private val service: IntraAuthService,
	private val preferences: AppPreferences
) : Authenticator {

	override fun authenticate(route: Route?, response: Response): Request? {
		val token = service.refreshToken().execute().body() ?: throw IllegalStateException()

		preferences.saveToken(token)
		return response.request().newBuilder()
			.header("Authorization", "Bearer ${token.access_token}")
			.build()
	}
}