package com.nalexand.friendlocation.utils.extensions

import android.app.Activity
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

fun Activity.softInputAdjustNothing() {
	window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
}

fun Activity.softInputAdjustResize() {
	window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
}

fun AppCompatActivity.printBackStack() {
	Log.d("bestTAG", "FRAGMENTS:\n" + supportFragmentManager
		.fragments
		.joinToString("\n") { fragment -> fragment.tag.toString() })
}
