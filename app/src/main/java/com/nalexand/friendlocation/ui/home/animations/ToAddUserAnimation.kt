package com.nalexand.friendlocation.ui.home.animations

import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import com.nalexand.friendlocation.ui.home.adapter.UserAdapter
import com.nalexand.friendlocation.utils.animator.*
import com.nalexand.friendlocation.utils.extensions.itemCount
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_home.view.*

class ToAddUserAnimation(
	viewGroup: ViewGroup,
	composite: CompositeDisposable
) : BaseAnimation(viewGroup, composite) {

	override fun start(onAnimationComplete: (() -> Unit)?) {
		viewGroup.fabAddUser.animateTranslation(300, AnimType.HIDE_RIGHT).mergeWith {
			val size = viewGroup.rvUsers.itemCount()
			if (size > 0) {
				animate(prepareParams(size), onAnimationComplete)
			} else {
				onAnimationComplete?.invoke()
			}
			it.onComplete()
		}.subscribe()
	}

	private fun prepareParams(size: Int): List<AnimParams> {
		val viewsForAnimation = mutableListOf<AnimParams>()
		val recycler = viewGroup.rvUsers

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

	private fun animate(viewsForAnimation: List<AnimParams>, onComplete: (() -> Unit)?) {
		viewsForAnimation.intervalAnim(300, AccelerateInterpolator()) { params ->
			params.run {
				view.animateTranslation(duration, type)
					.subscribe { if (last) onComplete?.invoke() }
			}
		}.subscribe { it.addTo(composite) }.addTo(composite)
	}
}