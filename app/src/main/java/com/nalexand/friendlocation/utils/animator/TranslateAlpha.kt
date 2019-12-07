package com.nalexand.friendlocation.utils.animator

import android.view.View
import androidx.core.view.ViewCompat
import com.nalexand.friendlocation.utils.animator.AnimType.HIDE
import com.nalexand.friendlocation.utils.animator.AnimType.SHOW
import io.reactivex.Completable
import io.reactivex.subjects.CompletableSubject
import java.lang.IllegalArgumentException

fun View.translateAlpha(duration: Long, type: Int): Completable {

	val throwable = IllegalArgumentException("INVALID TYPE")
	val animationSubject = CompletableSubject.create()

	val anim = ViewCompat.animate(this)
		.setDuration(duration)
		.withEndAction {
			animationSubject.onComplete()
		}

	val translateTo = when {
		type and SHOW == SHOW -> 100f
		type and HIDE == HIDE -> 0f
		else -> throw throwable
	}

	return animationSubject
		.doOnSubscribe {
			anim.alpha(translateTo).start()
		}
}