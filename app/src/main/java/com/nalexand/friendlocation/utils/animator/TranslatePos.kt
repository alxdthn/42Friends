package com.nalexand.friendlocation.utils.animator

import android.view.View
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
import io.reactivex.subjects.CompletableSubject
import java.lang.IllegalArgumentException

fun View.translatePos(duration: Long, type: Int): Completable {

	val throwable = IllegalArgumentException("INVALID TYPE")
	val animationSubject = CompletableSubject.create()
	val metrics = context.resources.displayMetrics
	val winWidth = metrics.widthPixels
	val winHeight = metrics.heightPixels

	val anim = ViewCompat.animate(this)
		.setDuration(duration)
		.withEndAction {
			animationSubject.onComplete()
		}

	val translateTo = when {
		type and HIDE == HIDE -> {
			when {
				type and HORIZONTAL != 0 -> {
					when {
						type and LEFT == LEFT -> x - winWidth
						type and RIGHT == RIGHT -> winWidth - x
						else -> throw throwable
					}
				}
				type and VERTICAL != 0 -> {
					when {
						type and TOP == TOP -> y - winHeight
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

	val preparedAnim = when {
		type and HORIZONTAL != 0 -> {
			anim.translationX(translateTo)
		}
		type and VERTICAL != 0 -> {
			anim.translationY(translateTo)
		}
		else -> throw IllegalArgumentException("Unknown type")
	}

	return animationSubject
		.doOnSubscribe {
			preparedAnim.start()
		}
}