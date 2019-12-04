package com.nalexand.friendlocation.network.service

import com.nalexand.friendlocation.model.request.TokenRequest
import com.nalexand.friendlocation.model.response.TokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IntraAuthService {

	@POST("oauth/token")
	fun refreshToken(
		@Body requestBody: TokenRequest = TokenRequest()
	): Call<TokenResponse>
}