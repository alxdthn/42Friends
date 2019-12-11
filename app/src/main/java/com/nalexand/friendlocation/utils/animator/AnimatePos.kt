package com.nalexand.friendlocation.utils.animator

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import com.nalexand.friendlocation.utils.animator.AnimType.POS_X
import com.nalexand.friendlocation.utils.animator.AnimType.POS_Y
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.CompletableSubject
import java.lang.IllegalArgumentException

fun View.animatePos(
	duration: Long,
	value: Float,
	type: Int,
	interpolator: Interpolator = AccelerateDecelerateInterpolator()
): Completable {

	val animSubject = CompletableSubject.create()
	val anim = animate()

	return animSubject
		.subscribeOn(AndroidSchedulers.mainThread())
		.doOnSubscribe {
			anim.setDuration(duration)
				.setInterpolator(interpolator)
				.withEndAction {
					animSubject.onComplete()
				}
			when (type) {
				POS_X -> anim.x(value)
				POS_Y -> anim.y(value)
				else -> throw IllegalArgumentException("INVALID TYPE")
			}
		}
		.doOnDispose {
			anim.cancel()
		}
}