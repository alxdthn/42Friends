package com.nalexand.friendlocation.utils.extensions

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.itemCount(): Int {
	return adapter?.itemCount ?: 0
}