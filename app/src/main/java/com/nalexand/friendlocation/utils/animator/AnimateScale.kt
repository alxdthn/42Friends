package com.nalexand.friendlocation.utils.animator

import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.core.view.ViewCompat
import com.nalexand.friendlocation.utils.animator.AnimType.SCALE_X
import com.nalexand.friendlocation.utils.animator.AnimType.SCALE_XY
import com.nalexand.friendlocation.utils.animator.AnimType.SCALE_Y
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.CompletableSubject
import java.lang.IllegalArgumentException

fun View.animateScale(
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
				SCALE_X -> anim.scaleX(value)
				SCALE_Y -> anim.scaleY(value)
				SCALE_XY -> anim.scaleX(value).scaleY(value)
				else -> throw IllegalArgumentException("INVALID TYPE")
			}
		}
		.doOnDispose {
			anim.cancel()
		}
}