package com.nalexand.friendlocation.ui.home.animations

import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.nalexand.friendlocation.ui.home.adapter.UserAdapter
import com.nalexand.friendlocation.utils.animator.AnimParams
import com.nalexand.friendlocation.utils.animator.AnimType
import com.nalexand.friendlocation.utils.animator.intervalAnim
import com.nalexand.friendlocation.utils.animator.animatePos
import com.nalexand.friendlocation.utils.extensions.itemCount
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class ToAddUserAnimation(private val composite: CompositeDisposable) {

	fun prepareAnim(fabAddUser: View, rvUsers: RecyclerView, onAnimationComplete: () -> Unit) {
		fabAddUser.animatePos(300, AnimType.HIDE_RIGHT).mergeWith {
			val size = rvUsers.itemCount()
			if (size > 0) {
				animate(prepareParams(rvUsers, size), onAnimationComplete)
			} else {
				onAnimationComplete()
			}
			it.onComplete()
		}.subscribe()
	}

	private fun prepareParams(recycler: RecyclerView, size: Int): List<AnimParams> {
		val viewsForAnimation = mutableListOf<AnimParams>()

		for (pos in 0..size) {
			val holder = recycler.findViewHolderForAdapterPosition(pos)
			if (holder is UserAdapter.ViewHolder) {
				val even = pos % 2 == 0
				val params = AnimParams(
					view = holder.itemView,
					duration = 500,
					type = if (even) AnimType.HIDE_LEFT else AnimType.HIDE_RIGHT
				)
				viewsForAnimation.add(params)
			}
		}
		viewsForAnimation.last().last = true
		return viewsForAnimation
	}

	private fun animate(viewsForAnimation: List<AnimParams>, onComplete: () -> Unit) {
		viewsForAnimation.intervalAnim(1000, LinearInterpolator()) { params ->
			params.run {
				view.animatePos(duration, type)
					.subscribe {
						if (last) onComplete()
					}.addTo(composite)
			}
		}.subscribe().addTo(composite)
	}
}