package com.nalexand.friendlocation.utils.animator

import android.animation.ValueAnimator
import android.view.animation.Animation
import android.widget.TextView
import io.reactivex.Completable
import io.reactivex.subjects.CompletableSubject

fun TextView.blinkedAnim(duration: Long): Completable {
	val animationSubject = CompletableSubject.create()
	val anim = ValueAnimator.ofFloat(20f, 0f, 20f).apply {
		repeatCount = Animation.INFINITE
	}

	anim.setDuration(duration)
		.addUpdateListener { valueAnim ->
			setShadowLayer(
				valueAnim.animatedValue as Float,
				shadowDx,
				shadowDy,
				shadowColor
			)
		}

	return animationSubject
		.doOnSubscribe {
			anim.start()
			animationSubject.onComplete()
		}
		.doOnDispose {
			anim.cancel()
		}
}