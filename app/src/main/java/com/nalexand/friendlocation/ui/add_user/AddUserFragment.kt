package com.nalexand.friendlocation.ui.add_user

import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.base.BaseFragment
import com.nalexand.friendlocation.model.recycler.UserItem
import com.nalexand.friendlocation.ui.add_user.animations.AddUserAnimationListener
import com.nalexand.friendlocation.utils.UserBinder
import com.nalexand.friendlocation.utils.UserBinder.getParams
import com.nalexand.friendlocation.utils.AppConstants.ERROR_INPUT
import com.nalexand.friendlocation.utils.AppConstants.ERROR_NETWORK
import com.nalexand.friendlocation.utils.AppConstants.ERROR_USER
import com.nalexand.friendlocation.utils.AppConstants.SUCCESS
import com.nalexand.friendlocation.utils.AppConstants.USER_EXISTS
import com.nalexand.friendlocation.utils.extensions.*
import kotlinx.android.synthetic.main.fragment_add_user.*

class AddUserFragment : BaseFragment<AddUserViewModel>(R.layout.fragment_add_user),
	View.OnClickListener, TextView.OnEditorActionListener {

	override fun initializeUi() {
		mainActivity.softInputAdjustResize()
		btnAddUser.setOnClickListener(this)
		txvAddUser.setOnClickListener(this)
		edxAddUser.apply {
			addTextChangedListener(viewModel)
			setOnEditorActionListener(this@AddUserFragment)
			requestFocus()
		}
	}

	override fun initializeObservers() {
		observe(viewModel.newUser) { user ->
			rvAddUserRotateView.bindAndStart { view ->
				val params = getParams(user.host)
				UserBinder.bindTo(view, UserItem().render(user, params))
			}
		}
		subscribe(viewModel.onHandleInput) { answerCode ->
			when (answerCode) {
				ERROR_USER -> {
					showSnackbar(clAddUserContent, R.string.cant_find_user)
				}
				ERROR_INPUT -> {
					showSnackbar(clAddUserContent, R.string.enter_username)
				}
				USER_EXISTS -> {
					showSnackbar(clAddUserContent, R.string.user_exists)
				}
				ERROR_NETWORK -> {
					showSnackbar(clAddUserContent, R.string.network_error)
				}
				SUCCESS -> {
					edxAddUser.setText("")
					showSnackbar(clAddUserContent, R.string.user_added)
				}
			}
		}
		observeState(viewModel.onLoading) { onLoading ->
			btnAddUser goneIf onLoading
			pbAddUser hereIf onLoading
		}
	}

	override fun onClick(v: View?) {
		when (v?.id) {
			R.id.btnAddUser -> viewModel.handleInput()
			R.id.txvAddUser -> viewModel.removeToken()
		}
	}

	override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
		return if (actionId == EditorInfo.IME_ACTION_DONE) {
			viewModel.handleInput()
			true
		} else {
			false
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		hideKeyboard()
	}

	override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
		if (nextAnim != 0) {
			val anim = AnimationUtils.loadAnimation(context, nextAnim)

			anim.setAnimationListener(
				AddUserAnimationListener(
					enter,
					clAddUserContent,
					composite
				)
			)
			return anim
		} else {
			cvAddUserHeader.translationY = 0f
			cvAddUserInput.translationY = 0f
		}
		return null
	}
}