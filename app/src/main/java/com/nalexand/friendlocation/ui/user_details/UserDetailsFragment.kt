package com.nalexand.friendlocation.ui.user_details

import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.base.BaseFragment
import com.nalexand.friendlocation.model.recycler.UserItem
import com.nalexand.friendlocation.utils.UserBinder
import com.nalexand.friendlocation.utils.extensions.observe
import com.nalexand.friendlocation.utils.extensions.printBackStack
import kotlinx.android.synthetic.main.user_item.*

class UserDetailsFragment : BaseFragment<UserDetailsViewModel>(
	UserDetailsViewModel::class.java,
	R.layout.fragment_user_details
) {

	override fun initializeUi() {}

	override fun initializeObservers() {
		mainActivity.printBackStack()
		observe(viewModel.user) {
			UserBinder.bindTo(cvUserItem, UserItem().render(it, UserBinder.getParams(it.host)))
		}
	}
}