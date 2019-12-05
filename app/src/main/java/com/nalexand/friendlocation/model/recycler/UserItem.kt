package com.nalexand.friendlocation.model.recycler

import com.nalexand.friendlocation.model.local.User

class UserItem(
	id: String = "",
	name: String = "",
	var host: String = "?"
) : Item(id, name) {

	infix fun render(user: User): UserItem {
		return this.apply {
			id = user.id
			name = user.login
			host = user.host ?: "?"
		}
	}

	override fun equals(other: Any?): Boolean {
		return other is UserItem
				&& id == other.id
				&& name == other.name
				&& host == other.host
	}
}