package com.nalexand.friendlocation.utils.animator

import android.view.View

data class AnimParams(
	val view: View,
	val duration: Long,
	val type: Int,
	var last: Boolean = false
)