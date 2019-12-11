package com.nalexand.friendlocation.ui.user_details.animations

import android.view.ViewGroup
import com.nalexand.friendlocation.utils.animator.AnimType.SHOW_BOTTOM
import com.nalexand.friendlocation.utils.animator.AnimType.SHOW_TOP
import com.nalexand.friendlocation.utils.animator.BaseAnimation
import com.nalexand.friendlocation.utils.animator.animateTranslation
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_user_details.view.*

class UserDetailsStartAnimation(
	viewGroup: ViewGroup,
	compositeDisposable: CompositeDisposable
) : BaseAnimation(viewGroup, compositeDisposable) {

	override fun start(onAnimationComplete: (() -> Unit)?) {
		viewGroup.apply {
			fabAddNote.animateTranslation(500, SHOW_BOTTOM)
				.mergeWith(cvUserInfoName.animateTranslation(500, SHOW_TOP))
				.subscribe().addTo(composite)
		}
	}
}