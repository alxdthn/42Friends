package com.nalexand.friendlocation.ui.add_user

import android.view.View
import android.view.animation.Animation
import com.nalexand.friendlocation.utils.animator.AnimType.SHOW_TOP
import com.nalexand.friendlocation.utils.animator.translatePos
import com.nalexand.friendlocation.utils.extensions.showKeyboard
import kotlinx.android.synthetic.main.fragment_add_user.view.*

class AddUserAnimationListener(private val enter: Boolean, private vararg val views: View) :
	Animation.AnimationListener {

	override fun onAnimationRepeat(animation: Animation?) {}

	override fun onAnimationEnd(animation: Animation?) {
		if (enter) {
			views[HEADER].translatePos(250, SHOW_TOP)
				.mergeWith(views[INPUT].translatePos(500, SHOW_TOP))
				.andThen { views[INPUT].edxAddUser.showKeyboard() }
				.subscribe()
		}
	}

	override fun onAnimationStart(animation: Animation?) {}

	companion object {
		const val HEADER = 0
		const val INPUT = 1
	}
}