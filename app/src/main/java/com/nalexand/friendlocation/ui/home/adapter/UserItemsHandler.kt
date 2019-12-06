package com.nalexand.friendlocation.ui.home.adapter

import android.view.View
import com.nalexand.friendlocation.base.BaseAdapter
import com.nalexand.friendlocation.base.BaseItemsHandler
import com.nalexand.friendlocation.model.local.User
import com.nalexand.friendlocation.model.recycler.Item
import com.nalexand.friendlocation.model.recycler.UserItem
import com.nalexand.friendlocation.ui.home.HomeFragment
import com.nalexand.friendlocation.utils.AppDiffUtil

@Suppress("UNCHECKED_CAST")
class UserItemsHandler(main: HomeFragment, adapter: BaseAdapter) :
	BaseItemsHandler(main.composite, DiffCallback(), adapter) {

	init {
		(adapter as UserAdapter).itemsHandler = this
	}

	override fun renderer(data: Iterable<Any>): List<Item> {
		val items = data as List<User>
		return items.map { UserItem() render it }
	}

	override fun onResult(result: List<Item>) {
	}

	override fun onItemClick(item: Item, view: View) {
	}

	class DiffCallback : AppDiffUtil.BaseDiffCallback() {

		override fun getChangePayload(oldPos: Int, newPos: Int): Any? {
			return null
		}
	}
}