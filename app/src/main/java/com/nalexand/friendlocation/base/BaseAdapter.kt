package com.nalexand.friendlocation.base

import androidx.recyclerview.widget.RecyclerView
import com.nalexand.friendlocation.model.recycler.Item

abstract class BaseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	abstract fun getItems(): List<Item>

	override fun getItemCount() = getItems().size

	override fun onBindViewHolder(
		holder: RecyclerView.ViewHolder,
		position: Int,
		payloads: MutableList<Any?>
	) {
		if (payloads.isEmpty()) {
			onBindViewHolder(holder, position)
		} else {
			onBindViewHolderWithPayloads(holder, payloads)
		}
	}

	abstract fun onBindViewHolderWithPayloads(
		holder: RecyclerView.ViewHolder,
		payloads: MutableList<Any?>
	)
}