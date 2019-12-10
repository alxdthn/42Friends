package com.nalexand.friendlocation.utils.animator

object AnimType {
	const val HIDE = 0x00000080
	const val SHOW = 0x00000040
	const val HORIZONTAL = 0x0000000C
	const val VERTICAL = 0x00000003
	const val LEFT = 0x00000008
	const val RIGHT = 0x00000004
	const val TOP = 0x00000002
	const val BOTTOM = 0x00000001
	const val HIDE_LEFT = HIDE or LEFT
	const val HIDE_RIGHT = HIDE or RIGHT
	const val HIDE_TOP = HIDE or TOP
	const val HIDE_BOTTOM = HIDE or BOTTOM
	const val SHOW_LEFT = SHOW or LEFT
	const val SHOW_RIGHT = SHOW or RIGHT
	const val SHOW_TOP = SHOW or TOP
	const val SHOW_BOTTOM = SHOW or BOTTOM

	const val SCALE_X = 1
	const val SCALE_Y = 2
	const val SCALE_XY = 3

	const val POS_X = 4
	const val POS_Y = 5
}