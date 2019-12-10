package com.nalexand.friendlocation.ui.home.animations

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nalexand.friendlocation.ui.home.adapter.UserAdapter
import com.nalexand.friendlocation.utils.animator.*
import com.nalexand.friendlocation.utils.animator.AnimType.HIDE_BOTTOM
import com.nalexand.friendlocation.utils.extensions.itemCount
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class ToUserDetailsAnimation(private val composite: CompositeDisposable) {

	fun prepareAnim(
		fabAddUser: View,
		clickedView: View,
		rvUsers: RecyclerView,
		onAnimationComplete: () -> Unit
	) {
		fabAddUser.animateTranslation(300, HIDE_BOTTOM).mergeWith {
			animate(prepareParams(rvUsers), clickedView, onAnimationComplete)
			it.onComplete()
		}.subscribe().addTo(composite)
	}

	private fun prepareParams(
		recycler: RecyclerView
	): List<AnimParams> {
		val size = recycler.itemCount()
		val viewsForAnimation = mutableListOf<AnimParams>()

		for (pos in 0..size) {
			val holder = recycler.findViewHolderForAdapterPosition(pos)
			if (holder is UserAdapter.ViewHolder) {
				val params = AnimParams(holder.itemView)
				viewsForAnimation.add(params)
			}
		}
		viewsForAnimation.last().last = true
		return viewsForAnimation
	}

	private fun animate(
		viewsForAnimation: List<AnimParams>,
		clickedView: View,
		onComplete: () -> Unit
	) {
		val scale = 0.85f

		viewsForAnimation.intervalAnim(1000) { params ->
			params.run {
				val anim = if (view == clickedView) {
					view.animatePos(500, 0f, AnimType.POS_Y)
				} else {
					view.animateScale(500, scale, AnimType.SCALE_XY)
						.andThen(view.animateTranslation(500, HIDE_BOTTOM))
				}
				anim.subscribe { if (last) onComplete() }
			}
		}.subscribe { it.addTo(composite) }.addTo(composite)
	}
}