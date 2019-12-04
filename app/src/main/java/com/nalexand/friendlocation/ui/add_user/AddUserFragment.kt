package com.nalexand.friendlocation.ui.add_user

import android.os.Bundle
import android.util.Log
import android.view.*
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.base.BaseFragment
import com.nalexand.friendlocation.ui.add_user.AddUserViewModel.Companion.ERROR_INPUT
import com.nalexand.friendlocation.ui.add_user.AddUserViewModel.Companion.ERROR_NETWORK
import com.nalexand.friendlocation.ui.add_user.AddUserViewModel.Companion.ERROR_USER
import com.nalexand.friendlocation.ui.add_user.AddUserViewModel.Companion.SUCCESS
import com.nalexand.friendlocation.ui.add_user.AddUserViewModel.Companion.USER_EXISTS
import com.nalexand.friendlocation.utils.extensions.*
import kotlinx.android.synthetic.main.fragment_add_user.*


class AddUserFragment : BaseFragment<AddUserViewModel>(R.layout.fragment_add_user), View.OnClickListener {

	override fun initializeObservers() {
		Log.d("bestTAG" , "init obs")
		subscribe(viewModel.onHandleInput) { answerCode ->
			Log.d("bestTAG" , "$answerCode")
			when (answerCode) {
				ERROR_USER -> { showSnackbar(clAddUserContent, R.string.cant_find_user) }
				ERROR_INPUT -> { showSnackbar(clAddUserContent, R.string.enter_username) }
				USER_EXISTS -> { showSnackbar(clAddUserContent, R.string.user_exists) }
				ERROR_NETWORK -> { showSnackbar(clAddUserContent, R.string.network_error) }
				SUCCESS -> { }
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
			showKeyboard()
		}
	}

	override fun onClick(v: View?) {
		when (v?.id) {
			R.id.btnAddUser -> viewModel.handleInput()
			R.id.txvAddUser -> viewModel.removeToken()
		}
	}
}