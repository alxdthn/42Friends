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
import kotlinx.android.synthetic.main.recycler_user_item.view.*

fun View.animateScale(
	duration: Long,
	value: Float,
	type: Int,
	interpolator: Interpolator = AccelerateDecelerateInterpolator()
): Completable {

	val animSubject = CompletableSubject.create()
	val anim = ViewCompat.animate(this)
		.setDuration(duration)
		.setInterpolator(interpolator)
		.withEndAction {
			Log.d("bestTAG", "asd")
			animSubject.onComplete()
		}

	return animSubject
		.subscribeOn(AndroidSchedulers.mainThread())
		.doOnSubscribe {
			Log.d("bestTAG", "SCALE: ${txvUserLogin.text}")
			when (type) {
				SCALE_X -> anim.scaleX(value)
				SCALE_Y -> anim.scaleY(value)
				SCALE_XY -> anim.scaleX(value).scaleY(value)
			}
		}
}