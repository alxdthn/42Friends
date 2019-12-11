package com.nalexand.friendlocation.utils.animator

import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable

abstract class BaseAnimation(
	val viewGroup: ViewGroup,
	val composite: CompositeDisposable
) {
	abstract fun start(onAnimationComplete: (() -> Unit)? = null)
}