package com.nalexand.friendlocation.ui.home.adapter.touch_helper

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.nalexand.friendlocation.ui.home.adapter.UserAdapter

class TouchHelperCallback(
	private val listener: TouchHelperInterface,
	private val onSwipeDrawer: OnSwipeDrawer
) : ItemTouchHelper.Callback() {

	private val swipeThreshold = 0.7f
	private val swipeEscapeVelocity = 100000f
	private val swipeVelocityThreshold = 0f

	override fun onChildDraw(
		c: Canvas,
		recyclerView: RecyclerView,
		viewHolder: RecyclerView.ViewHolder,
		dX: Float,
		dY: Float,
		actionState: Int,
		isCurrentlyActive: Boolean
	) {
		onSwipeDrawer.draw(viewHolder, c, dX)
		super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
	}

	override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
		if (viewHolder is UserAdapter.ViewHolder) {
			listener.onItemSwipe(viewHolder.adapterPosition)
		}
	}

	override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
		super.onSelectedChanged(viewHolder, actionState)

		if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
			if (viewHolder is TouchHelperViewHolder) {
				viewHolder.onItemSelected()
			}
		}
	}

	override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
		if (viewHolder is TouchHelperViewHolder) {
			viewHolder.onItemClear()
		}
	}

	override fun getMovementFlags(p0: RecyclerView, p1: RecyclerView.ViewHolder) =
		makeMovementFlags(0, ItemTouchHelper.START or ItemTouchHelper.END)

	override fun isLongPressDragEnabled() = false
	override fun getSwipeVelocityThreshold(defaultValue: Float) = swipeVelocityThreshold
	override fun getSwipeEscapeVelocity(defaultValue: Float) = swipeEscapeVelocity
	override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder) = swipeThreshold
	override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder) = false
}