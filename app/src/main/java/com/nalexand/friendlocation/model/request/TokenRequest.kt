package com.nalexand.friendlocation.model.request

import com.nalexand.friendlocation.utils.AppConstants.CLIENT_ID
import com.nalexand.friendlocation.utils.AppConstants.CLIENT_SECRET
import com.nalexand.friendlocation.utils.AppConstants.GRAND_TYPE

data class TokenRequest(
	val grant_type: String = GRAND_TYPE,
	val client_id: String = CLIENT_ID,
	val client_secret: String = CLIENT_SECRET
)