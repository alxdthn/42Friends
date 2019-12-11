package com.nalexand.friendlocation.ui.home.animations

import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.nalexand.friendlocation.ui.home.adapter.UserAdapter
import com.nalexand.friendlocation.utils.animator.*
import com.nalexand.friendlocation.utils.animator.AnimType.HIDE_BOTTOM
import com.nalexand.friendlocation.utils.extensions.itemCount
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_home.view.*

class ToUserDetailsAnimation(
	viewGroup: ViewGroup,
	composite: CompositeDisposable
) : BaseAnimation(viewGroup, composite){

	private lateinit var clickedView: View

	fun startWith(clickedView: View, onAnimationComplete: (() -> Unit)?) {
		this.clickedView = clickedView
		start(onAnimationComplete)
	}

	override fun start(onAnimationComplete: (() -> Unit)?) {
		viewGroup.fabAddUser.animateTranslation(300, HIDE_BOTTOM).mergeWith {
			animate(prepareParams(), clickedView, onAnimationComplete)
			it.onComplete()
		}.subscribe().addTo(composite)
	}

	private fun prepareParams(): List<AnimParams> {
		val recycler = viewGroup.rvUsers
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
		onComplete: (() -> Unit)?
	) {
		val scale = 0.85f

		viewsForAnimation.intervalAnim(400) { params ->
			params.run {
				val anim = if (view == clickedView) {
					view.animatePos(500, 0f, AnimType.POS_Y)
				} else {
					view.animateScale(500, scale, AnimType.SCALE_XY)
						.andThen(view.animateTranslation(500, HIDE_BOTTOM, AccelerateInterpolator()))
				}
				anim.subscribe { if (last) onComplete?.invoke() }
			}
		}.subscribe { it.addTo(composite) }.addTo(composite)
	}
}