package com.nalexand.friendlocation.base

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.nalexand.friendlocation.main.App
import com.nalexand.friendlocation.main.MainActivity
import com.nalexand.friendlocation.main.CommonViewModel
import com.nalexand.friendlocation.ui.add_user.AddUserDialog

abstract class BaseDialogFragment : DialogFragment() {

	lateinit var commonViewModel: CommonViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		inject()
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)

		commonViewModel = (activity as MainActivity).commonViewModel
	}

	private fun inject() {
		val injector = (activity?.application as App).getAppComponent()
		when (this) {
			is AddUserDialog -> injector.inject(this)
		}
	}
}