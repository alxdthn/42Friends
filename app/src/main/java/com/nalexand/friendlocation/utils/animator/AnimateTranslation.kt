package com.nalexand.friendlocation.utils.animator

import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.core.view.ViewCompat
import com.nalexand.friendlocation.utils.animator.AnimType.BOTTOM
import com.nalexand.friendlocation.utils.animator.AnimType.HIDE
import com.nalexand.friendlocation.utils.animator.AnimType.HORIZONTAL
import com.nalexand.friendlocation.utils.animator.AnimType.LEFT
import com.nalexand.friendlocation.utils.animator.AnimType.RIGHT
import com.nalexand.friendlocation.utils.animator.AnimType.SHOW
import com.nalexand.friendlocation.utils.animator.AnimType.TOP
import com.nalexand.friendlocation.utils.animator.AnimType.VERTICAL
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.CompletableSubject

fun View.animateTranslation(
	duration: Long,
	type: Int,
	interpolator: Interpolator = AccelerateDecelerateInterpolator()
): Completable {

	val throwable = IllegalArgumentException("INVALID TYPE")
	val metrics = context.resources.displayMetrics
	val winWidth = metrics.widthPixels
	val winHeight = metrics.heightPixels
	val animationSubject = CompletableSubject.create()

	return animationSubject
		.doOnSubscribe {
			val anim = ViewCompat.animate(this)
				.setDuration(duration)
				.setInterpolator(interpolator)
				.withEndAction {
					animationSubject.onComplete()
				}

			val translateTo = when {
				type and HIDE == HIDE -> {
					when {
						type and HORIZONTAL != 0 -> {
							when {
								type and LEFT == LEFT -> -(x + width)
								type and RIGHT == RIGHT -> (winWidth + width).toFloat()
								else -> throw throwable
							}
						}
						type and VERTICAL != 0 -> {
							when {
								type and TOP == TOP -> -(y + height)
								type and BOTTOM == BOTTOM -> winHeight - y
								else -> throw throwable
							}
						}
						else -> throw throwable
					}
				}
				type and SHOW == SHOW -> 0f
				else -> throw throwable
			}

			when {
				type and HORIZONTAL != 0 -> {
					anim.translationX(translateTo)
				}
				type and VERTICAL != 0 -> {
					anim.translationY(translateTo)
				}
				else -> throw IllegalArgumentException("Unknown type")
			}
		}.subscribeOn(AndroidSchedulers.mainThread())
}