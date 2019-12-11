package com.nalexand.friendlocation.ui.home.animations

import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.EditText
import android.widget.TextView
import com.nalexand.friendlocation.utils.animator.AnimType.HIDE
import com.nalexand.friendlocation.utils.animator.AnimType.SHOW
import com.nalexand.friendlocation.utils.animator.BaseAnimation
import com.nalexand.friendlocation.utils.animator.animateAlpha
import com.nalexand.friendlocation.utils.extensions.gone
import com.nalexand.friendlocation.utils.extensions.visible
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_home.view.*

class OnUserSwipeAnimation(
	viewGroup: ViewGroup,
	compositeDisposable: CompositeDisposable
) : BaseAnimation(viewGroup, compositeDisposable) {

	private lateinit var swipedView: View
	private val btnAccept = viewGroup.btnAcceptRemoveUser
	private val btnDismiss = viewGroup.btnDismissRemoveUser

	fun startWith(swipedView: View) {
		this.swipedView = swipedView
		start()
	}

	override fun start(onAnimationComplete: (() -> Unit)?) {
		prepareButtons()
		animate()
	}

	fun reverse() {
		btnDismiss.elevation = -1f
		btnAccept.elevation = -1f
		btnAccept.animateAlpha(0, HIDE)
			.mergeWith(btnDismiss.animateAlpha(0, HIDE))
			.subscribe {
				btnDismiss.gone()
				btnAccept.gone()
			}.addTo(composite)
	}

	private fun animate() {
		btnAccept.animate()
			.setDuration(300)
			.setInterpolator(LinearInterpolator())
			.alpha(100f)
	/*	btnAccept.animateAlpha(0, SHOW)
			.mergeWith(btnDismiss.animateAlpha(0, SHOW))
			.subscribe().addTo(composite)*/
	}

	private fun prepareButtons() {
		swipedView.apply {
			prepareButton(btnAccept, y, height)
			prepareButton(btnDismiss, y, height)
		}
	}

	private fun prepareButton(button: TextView, newY: Float, newHeight: Int) {
		button.apply {
			visible()
			alpha = 0f
			height = newHeight
			width = newHeight
			y = newY
			elevation = 1f
		}
	}
}