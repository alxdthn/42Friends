package com.nalexand.friendlocation.ui.home

import android.view.View
import androidx.navigation.fragment.findNavController
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.base.BaseFragment
import com.nalexand.friendlocation.model.recycler.Item
import com.nalexand.friendlocation.ui.home.adapter.UserItemsHandler
import com.nalexand.friendlocation.ui.home.animations.ToAddUserAnimation
import com.nalexand.friendlocation.ui.home.animations.ToUserDetailsAnimation
import com.nalexand.friendlocation.utils.AppConstants.ERROR_NETWORK
import com.nalexand.friendlocation.utils.extensions.observe
import com.nalexand.friendlocation.utils.extensions.observeState
import com.nalexand.friendlocation.utils.extensions.showSnackbar
import com.nalexand.friendlocation.utils.extensions.subscribe
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<HomeViewModel>(HomeViewModel::class.java, R.layout.fragment_home),
	View.OnClickListener {

	private lateinit var itemsHandler: UserItemsHandler

	private val toAddUserAnimation = ToAddUserAnimation(getComposite())
	private val toUserDetailsAnimation = ToUserDetailsAnimation(getComposite())

	override fun initializeUi() {
		itemsHandler = UserItemsHandler(this)
		rvUsers.adapter = itemsHandler.adapter
		fabAddUser.setOnClickListener(this)
		srlUsers.setOnRefreshListener {
			viewModel.refreshing.start()
			viewModel.updateLocations()
		}
	}

	override fun initializeObservers() {
		subscribe(viewModel.errors) { errorCode ->
			if (errorCode == ERROR_NETWORK) {
				showSnackbar(clHomeContent, R.string.network_error)
			}
		}
		observeState(viewModel.refreshing) { updating ->
			srlUsers.isRefreshing = updating
		}
		observe(viewModel.users) { users ->
			itemsHandler render users
		}
	}

	fun onUserClick(userItem: Item, clickedView: View) {
		toUserDetailsAnimation.prepareAnim(fabAddUser, clickedView, rvUsers) {
			viewModel.onNavigateToUserDetails(userItem.id)
			findNavController().navigate(R.id.action_nav_home_to_nav_user_details)
		}
	}

	override fun onClick(v: View?) {
		when (v?.id) {
			R.id.fabAddUser -> {
				toAddUserAnimation.prepareAnim(fabAddUser, rvUsers) {
					findNavController().navigate(R.id.action_nav_home_to_nav_add_user)
				}
			}
		}
	}
}