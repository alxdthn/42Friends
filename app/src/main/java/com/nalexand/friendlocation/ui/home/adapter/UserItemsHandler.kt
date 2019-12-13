package com.nalexand.friendlocation.ui.home.adapter

import android.view.View
import com.nalexand.friendlocation.base.BaseAdapter
import com.nalexand.friendlocation.base.BaseItemsHandler
import com.nalexand.friendlocation.model.local.User
import com.nalexand.friendlocation.model.recycler.Item
import com.nalexand.friendlocation.model.recycler.UserItem
import com.nalexand.friendlocation.ui.home.HomeFragment
import com.nalexand.friendlocation.ui.home.adapter.touch_helper.TouchHelperCallback
import com.nalexand.friendlocation.ui.home.adapter.touch_helper.TouchHelperInterface
import com.nalexand.friendlocation.utils.UserBinder.getParams
import com.nalexand.friendlocation.utils.AppDiffUtil

@Suppress("UNCHECKED_CAST")
class UserItemsHandler(private val main: HomeFragment) :
	BaseItemsHandler(main.getComposite(), DiffCallback(), UserAdapter()),
	TouchHelperInterface {

	init {
		(adapter as UserAdapter).itemsHandler = this
	}

	override fun renderer(data: Iterable<Any>): List<Item> {
		val items = (data as List<User>).sortedWith(compareBy(
			{ it.host == null },
			{ it.login }
		))
		return items.map { user ->
			UserItem().render(user, getParams(user.host))
		}
	}


	override fun onItemClick(item: Item, view: View) {
		main.onUserClick(item, view)
	}

	override fun onItemSwipe(position: Int): Boolean {
		return main.onUserSwipe(position)
	}

	override fun onClickRight() {}

	override fun onClickLeft() {}

	class DiffCallback : AppDiffUtil.BaseDiffCallback()
}