package com.nalexand.friendlocation.ui.home.adapter.touch_helper

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.nalexand.friendlocation.ui.home.adapter.UserAdapter

class TouchHelperCallback(
	private val listener: TouchHelperInterface,
	private val onSwipeDrawer: OnSwipeDrawer
) : ItemTouchHelper.Callback() {

	override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder) = 0.7f

	override fun getSwipeEscapeVelocity(defaultValue: Float) = 100000f

	override fun getSwipeVelocityThreshold(defaultValue: Float) = 0f

	override fun onChildDraw(
		c: Canvas,
		recyclerView: RecyclerView,
		viewHolder: RecyclerView.ViewHolder,
		dX: Float,
		dY: Float,
		actionState: Int,
		isCurrentlyActive: Boolean
	) {
		onSwipeDrawer.draw(viewHolder, c, dX, isCurrentlyActive)
		super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
	}

	override fun getMovementFlags(
		recyclerView: RecyclerView,
		viewHolder: RecyclerView.ViewHolder
	): Int {
			val swipeFlags = ItemTouchHelper.START or
					ItemTouchHelper.END
			return makeMovementFlags(0, swipeFlags)
	}

	override fun isLongPressDragEnabled(): Boolean {
		return false
	}

	override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
		if (viewHolder is UserAdapter.ViewHolder) {
			listener.onItemSwipe(viewHolder.adapterPosition)
		}
	}

	override fun onMove(
		recyclerView: RecyclerView,
		viewHolder: RecyclerView.ViewHolder,
		target: RecyclerView.ViewHolder
	): Boolean {
		return false
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
}
