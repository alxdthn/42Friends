package com.nalexand.friendlocation.ui.add_user

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.base.BaseFragment
import com.nalexand.friendlocation.ui.add_user.AddUserViewModel.Companion.ERROR_INPUT
import com.nalexand.friendlocation.ui.add_user.AddUserViewModel.Companion.ERROR_NETWORK
import com.nalexand.friendlocation.ui.add_user.AddUserViewModel.Companion.ERROR_USER
import com.nalexand.friendlocation.ui.add_user.AddUserViewModel.Companion.SUCCESS
import com.nalexand.friendlocation.ui.add_user.AddUserViewModel.Companion.USER_EXISTS
import com.nalexand.friendlocation.utils.extensions.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_add_user.*


class AddUserFragment : BaseFragment<AddUserViewModel>(R.layout.fragment_add_user),
	View.OnClickListener {

	override fun initializeObservers() {
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
					showSnackbar(clAddUserContent, R.string.user_added)
				}
			}
		}
		observeState(viewModel.onLoading) { onLoading ->
			btnAddUser goneIf onLoading
			pbAddUser hereIf onLoading
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		mainActivity.softInputAdjustResize()
		btnAddUser.setOnClickListener(this)
		txvAddUser.setOnClickListener(this)
		edxAddUser.apply {
			addTextChangedListener(viewModel)
			requestFocus()
		}
	}

	override fun onClick(v: View?) {
		when (v?.id) {
			R.id.btnAddUser -> viewModel.handleInput()
			R.id.txvAddUser -> viewModel.removeToken()
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
					cvAddUserHeader,
					cvAddUserInput
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