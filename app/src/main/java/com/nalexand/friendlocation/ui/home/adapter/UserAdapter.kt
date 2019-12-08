package com.nalexand.friendlocation.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.base.BaseAdapter
import com.nalexand.friendlocation.model.recycler.Item
import com.nalexand.friendlocation.model.recycler.UserItem
import kotlinx.android.synthetic.main.recycler_user_item.view.*

@Suppress("UNCHECKED_CAST")
class UserAdapter : BaseAdapter() {

	lateinit var itemsHandler: UserItemsHandler

	override fun getItems() = itemsHandler.items as List<UserItem>

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return ViewHolder(
			LayoutInflater.from(parent.context).inflate(
				R.layout.recycler_user_item,
				parent,
				false
			)
		)
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		if (holder is ViewHolder) {
			val item = getItems()[position]
			holder.bindTo(item, itemsHandler::onItemClick)
		}
	}

	override fun onBindViewHolderWithPayloads(
		holder: RecyclerView.ViewHolder,
		payloads: MutableList<Any?>
	) {
		if (holder is ViewHolder) {
			holder.bindTo(payloads)
		}
	}

	class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
		fun bindTo(userItem: UserItem, onClick: (Item, View) -> Unit) {
			UserBinder.bindTo(itemView, userItem, onClick)
		}

		fun bindTo(payloads: MutableList<Any?>) {
			UserBinder.bindTo(itemView, payloads)
		}
	}
}