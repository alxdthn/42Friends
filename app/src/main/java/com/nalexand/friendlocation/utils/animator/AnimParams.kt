package com.nalexand.friendlocation.utils.animator

import android.view.View

data class AnimParams(
	val view: View,
	val type: Int,
	val duration: Long = 500,
	var last: Boolean = false,
	var pos: Int = 0
)