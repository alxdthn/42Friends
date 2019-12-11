package com.nalexand.friendlocation.utils.animator

import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.view.ViewCompat
import com.nalexand.friendlocation.utils.animator.AnimType.HIDE
import com.nalexand.friendlocation.utils.animator.AnimType.SHOW
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.CompletableSubject
import java.lang.IllegalArgumentException

fun View.animateAlpha(duration: Long, type: Int): Completable {

	val throwable = IllegalArgumentException("INVALID TYPE")
	val animationSubject = CompletableSubject.create()

	val anim = animate()

	return animationSubject
		.doOnSubscribe {
			val translateTo = when {
				type and SHOW == SHOW -> 100f
				type and HIDE == HIDE -> 0f
				else -> throw throwable
			}
			anim.setInterpolator(LinearInterpolator())
				.withEndAction {
					animationSubject.onComplete()
				}.alpha(translateTo)
		}.doOnDispose {
			anim.cancel()
		}.subscribeOn(AndroidSchedulers.mainThread())
}