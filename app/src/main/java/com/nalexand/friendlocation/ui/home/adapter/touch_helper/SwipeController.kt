package com.nalexand.friendlocation.ui.home.adapter.touch_helper

import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.RecyclerView

class SwipeController(
	private val listener: TouchHelperInterface,
	private val drawer: SwipeDrawer
) : (Canvas, RecyclerView, RecyclerView.ViewHolder, Float, Float, Int, Boolean) -> Unit,
	View.OnTouchListener {

	private var itemWidth = 0
	private var itemHeight = 0
	private var buttonsVisible = false
	private val leftArea = Area()
	private val rightArea = Area()

	override fun invoke(
		c: Canvas,
		recyclerView: RecyclerView,
		viewHolder: RecyclerView.ViewHolder,
		dX: Float,
		dY: Float,
		actionState: Int,
		isCurrentlyActive: Boolean
	) {
		if (actionState == ACTION_STATE_SWIPE) {
			val itemView = viewHolder.itemView

			itemHeight = itemView.bottom - itemView.top
			itemWidth = itemView.right - itemView.left

			buttonsVisible = drawContent(c, itemView.top, dX)
		}
	}

	override fun onTouch(v: View?, event: MotionEvent): Boolean {
		val needHandleTouch = event.action == MotionEvent.ACTION_DOWN && buttonsVisible
		if (needHandleTouch) {



			return when (calculateTouchArea(event.x, event.y)) {
				LEFT_AREA -> {
					listener.onClickLeft()
					true
				}
				RIGHT_AREA -> {
					listener.onClickRight()
					true
				}
				else -> false
			}
		}
		return needHandleTouch
	}

	private fun calculateTouchArea(x: Float, y: Float): Int {

	}

	private fun drawContent(c: Canvas, top: Int, dX: Float): Boolean {
		return drawer.run {
			setParams(c, top, dX, itemHeight, itemWidth)
			drawMessage()
			drawButtons()
		}
	}

	class Area(
		var x: Int = 0,
		var y: Int = 0,
		var height: Int = 0,
		var width: Int = 0
	) {

	}

	private companion object {
		const val LEFT_AREA = 1
		const val RIGHT_AREA = 2
	}
}