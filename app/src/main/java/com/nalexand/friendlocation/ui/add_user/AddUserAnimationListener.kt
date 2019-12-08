package com.nalexand.friendlocation.ui.add_user

import android.animation.ValueAnimator
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.EditText
import android.widget.TextView
import com.nalexand.friendlocation.utils.animator.AnimType.SHOW_TOP
import com.nalexand.friendlocation.utils.animator.blinkedAnim
import com.nalexand.friendlocation.utils.animator.translatePos
import com.nalexand.friendlocation.utils.extensions.showKeyboard
import io.reactivex.Completable
import io.reactivex.subjects.CompletableSubject
import io.reactivex.subjects.SingleSubject
import kotlinx.android.synthetic.main.fragment_add_user.view.*

class AddUserAnimationListener(private val enter: Boolean, private val group: ViewGroup) :
	Animation.AnimationListener {

	override fun onAnimationRepeat(animation: Animation?) {}

	override fun onAnimationEnd(animation: Animation?) {
		if (enter) {
			group.cvAddUserHeader.translatePos(250, SHOW_TOP)
				.mergeWith(group.cvAddUserInput.translatePos(500, SHOW_TOP))
				.mergeWith(group.flAddUserButton.translatePos(1000, SHOW_TOP))
				.mergeWith(showKeyboard(group.edxAddUser))
				.andThen(group.btnAddUser.blinkedAnim(5000))
				.subscribe()
		}
	}

	private fun showKeyboard(view: EditText): Completable {
		val keyboardSubject = CompletableSubject.create()

		return keyboardSubject.doOnSubscribe {
			view.showKeyboard()
			keyboardSubject.onComplete()
		}
	}

	override fun onAnimationStart(animation: Animation?) {}
}