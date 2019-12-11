package com.nalexand.friendlocation.utils.animator

import android.view.animation.Animation

open class BaseAnimationListener(
	private val enter: Boolean,
	private val anim: BaseAnimation
) : Animation.AnimationListener {

	override fun onAnimationRepeat(animation: Animation?) {}

	override fun onAnimationEnd(animation: Animation?) {
		if (enter) {
			anim.start()
		}
	}

	override fun onAnimationStart(animation: Animation?) {}
}
