package com.nalexand.friendlocation.ui.home.animations

import android.view.View
import android.view.ViewGroup
import com.nalexand.friendlocation.utils.animator.BaseAnimation
import io.reactivex.disposables.CompositeDisposable

class OnUserSwipeAnimation(
	viewGroup: ViewGroup,
	compositeDisposable: CompositeDisposable
) : BaseAnimation(viewGroup, compositeDisposable) {

	private lateinit var swipedView: View

	fun startWith(swipedView: View) {
		this.swipedView = swipedView
		start()
	}

	override fun start(onAnimationComplete: (() -> Unit)?) {

	}
}