package com.nalexand.friendlocation.utils.extensions

import android.view.View
import com.nalexand.friendlocation.utils.AppAnimator

infix fun AppAnimator.show(view: View) {
	animateAlpha(view, SHOW)
}

infix fun AppAnimator.hide(view: View) {
	animateAlpha(view, HIDE)
}
