package com.nalexand.friendlocation.ui.add_user.animations

import android.view.ViewGroup
import android.widget.EditText
import com.nalexand.friendlocation.utils.animator.AnimType
import com.nalexand.friendlocation.utils.animator.blinkedAnim
import com.nalexand.friendlocation.utils.animator.animateTranslation
import com.nalexand.friendlocation.utils.extensions.showKeyboard
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.CompletableSubject
import kotlinx.android.synthetic.main.fragment_add_user.view.*

class AddUserStartAnimation(private val composite: CompositeDisposable) {

	fun start(group: ViewGroup) {
		group.apply {
			cvAddUserHeader.animateTranslation(250, AnimType.SHOW_TOP)
				.mergeWith(cvAddUserInput.animateTranslation(500, AnimType.SHOW_TOP))
				.mergeWith(flAddUserButton.animateTranslation(1000, AnimType.SHOW_TOP))
				.mergeWith(showKeyboard(edxAddUser))
				.andThen(btnAddUser.blinkedAnim(5000))
				.subscribe().addTo(composite)
		}
	}

	private fun showKeyboard(view: EditText): Completable {
		val keyboardSubject = CompletableSubject.create()

		return keyboardSubject.doOnSubscribe {
			view.showKeyboard()
			keyboardSubject.onComplete()
		}
	}
}