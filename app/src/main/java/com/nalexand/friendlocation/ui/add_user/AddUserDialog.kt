package com.nalexand.friendlocation.ui.add_user

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.base.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_user_add.*
import kotlinx.android.synthetic.main.dialog_user_add.view.*
import javax.inject.Inject

class AddUserDialog : BaseDialogFragment(), View.OnClickListener {

	@Inject
	lateinit var viewModel: AddUserViewModel

	private lateinit var dialogView: View

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		val builder = AlertDialog.Builder(context)
		val dialog = builder.create()

		dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_user_add, null)
		dialogView.btnAddUser.setOnClickListener(this)
		dialog.setView(dialogView)
		return dialog
	}

	override fun onClick(v: View?) {
		when (v?.id) {
			R.id.btnAddUser -> viewModel.addUser(dialogView.edxAddUser.text)
		}
	}
}