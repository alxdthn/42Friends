package com.nalexand.friendlocation.ui.home.adapter.touch_helper

interface TouchHelperInterface {
	fun onItemSwipe(position: Int): Boolean
	fun onClickRight()
	fun onClickLeft()
}