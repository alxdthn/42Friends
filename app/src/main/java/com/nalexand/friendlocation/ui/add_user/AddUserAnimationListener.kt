package com.nalexand.friendlocation.ui.add_user

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.Animation
import androidx.core.animation.doOnEnd
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.utils.extensions.showKeyboard
import kotlinx.android.synthetic.main.fragment_add_user.view.*

class AddUserAnimationListener(private val enter: Boolean, private vararg val views: View) :
	Animation.AnimationListener {

	override fun onAnimationRepeat(animation: Animation?) {}

	override fun onAnimationEnd(animation: Animation?) {
		if (enter) {
			translateY(views[HEADER], 0f, 250)
			translateY(views[INPUT], 0f,500)
		}
	}

	override fun onAnimationStart(animation: Animation?) {}

	private fun translateY(view: View, toDeltaY: Float, duration: Long) {
		ObjectAnimator.ofFloat(view, "translationY", toDeltaY).apply {
			this.duration = duration
			doOnEnd {
				if (view.id == R.id.cvAddUserInput) {
					view.edxAddUser.showKeyboard()
				}
			}
			start()
		}
	}

	companion object {
		const val HEADER = 0
		const val INPUT = 1
	}
}