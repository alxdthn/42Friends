package com.nalexand.friendlocation.ui.add_user.animations

import android.view.ViewGroup
import android.view.animation.Animation
import io.reactivex.disposables.CompositeDisposable

class AddUserAnimationListener(
	private val enter: Boolean,
	private val group: ViewGroup,
	private val composite: CompositeDisposable
) : Animation.AnimationListener {

	override fun onAnimationRepeat(animation: Animation?) {}

	override fun onAnimationEnd(animation: Animation?) {
		if (enter) {
			AddUserStartAnimation(composite).start(group)
		}
	}

	override fun onAnimationStart(animation: Animation?) {}
}