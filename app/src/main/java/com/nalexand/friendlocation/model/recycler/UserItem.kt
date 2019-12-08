package com.nalexand.friendlocation.model.recycler

import com.nalexand.friendlocation.model.local.User
import com.nalexand.friendlocation.ui.home.adapter.UserItemsHandler
import java.util.*

class UserItem(
	id: String = "",
	name: String = "",
	var host: String = "?",
	var color: Int = 0,
	var shadow: Float = 0f
) : Item(id, name) {

	fun render(user: User, params: Params): UserItem {
		return this.apply {
			id = user.id
			name = user.login.toUpperCase(Locale.ENGLISH)
			host = user.host?.toUpperCase(Locale.ENGLISH) ?: "?"
			color = params.color
			shadow = params.shadow
		}
	}

	override fun equals(other: Any?): Boolean {
		return other is UserItem
				&& id == other.id
				&& name == other.name
				&& host == other.host
	}

	data class Params(
		val color: Int,
		val shadow: Float
	)
}