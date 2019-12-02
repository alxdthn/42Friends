package com.nalexand.friendlocation.utils.extensions

import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nalexand.friendlocation.R

fun SwipeRefreshLayout.setAppColors() {
	setColorSchemeColors(
		ContextCompat.getColor(context, R.color.colorAccent),
		ContextCompat.getColor(context, R.color.colorPrimaryDark),
		ContextCompat.getColor(context, R.color.colorAccent),
		ContextCompat.getColor(context, R.color.colorPrimaryDark)
	)
}