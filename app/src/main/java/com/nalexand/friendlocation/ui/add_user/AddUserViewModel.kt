package com.nalexand.friendlocation.ui.add_user

import android.util.Log
import com.nalexand.friendlocation.base.BaseViewModel
import javax.inject.Inject

class AddUserViewModel @Inject constructor(): BaseViewModel() {

	fun addUser(input: String) {
		Log.d("bestTAG", input)
	}

	override fun onViewCreated() {}
}