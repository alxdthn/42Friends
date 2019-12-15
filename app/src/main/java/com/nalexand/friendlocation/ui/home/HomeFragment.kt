package com.nalexand.friendlocation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.base.BaseFragment
import com.nalexand.friendlocation.model.recycler.Item
import com.nalexand.friendlocation.ui.home.adapter.UserItemsHandler
import com.nalexand.friendlocation.ui.home.adapter.touch_helper.TouchHelperCallback
import com.nalexand.friendlocation.ui.home.adapter.touch_helper.SwipeDrawer
import com.nalexand.friendlocation.ui.home.adapter.touch_helper.SwipeController
import com.nalexand.friendlocation.ui.home.animations.ToAddUserAnimation
import com.nalexand.friendlocation.ui.home.animations.ToUserDetailsAnimation
import com.nalexand.friendlocation.utils.AppConstants.ERROR_NETWORK
import com.nalexand.friendlocation.utils.extensions.observe
import com.nalexand.friendlocation.utils.extensions.observeState
import com.nalexand.friendlocation.utils.extensions.showSnackbar
import com.nalexand.friendlocation.utils.extensions.subscribe
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.IllegalStateException

class HomeFragment : BaseFragment<HomeViewModel>(HomeViewModel::class.java, R.layout.fragment_home),
	View.OnClickListener {

	private lateinit var itemsHandler: UserItemsHandler

	private lateinit var toAddUserAnimation: ToAddUserAnimation
	private lateinit var toUserDetailsAnimation: ToUserDetailsAnimation

	private var tmpPos = 0

	override fun initializeUi() {
		initializeAnimations()
		initializeRecycler()
		fabAddUser.setOnClickListener(this)
		srlUsers.setOnRefreshListener {
			viewModel.refreshing.start()
			viewModel.updateLocations()
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (savedInstanceState == null) {
			viewModel.getUsers()
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
			if (!viewModel.refreshing.isActive()) {
				Log.d("bestTAG", "2")
				itemsHandler render users
			}
		}
	}

	private fun initializeRecycler() {
		itemsHandler = UserItemsHandler(this)
		val swipeDrawer = SwipeDrawer(mainActivity.applicationContext)
		val swipeController = SwipeController(itemsHandler, swipeDrawer)
		val touchHelperCallback = TouchHelperCallback(itemsHandler, swipeController)
		ItemTouchHelper(touchHelperCallback).attachToRecyclerView(rvUsers)
		rvUsers.apply {
			adapter = itemsHandler.adapter
			setOnTouchListener(swipeController)
		}
	}

	private fun initializeAnimations() {
		toAddUserAnimation = ToAddUserAnimation(clHomeContent, getComposite())
		toUserDetailsAnimation = ToUserDetailsAnimation(clHomeContent, getComposite())
	}

	private fun navigateToUserDetails(userItem: Item, clickedView: View) {
		toUserDetailsAnimation.startWith(clickedView) {
			rvUsers.isLayoutFrozen = true
			viewModel.onNavigateToUserDetails(userItem.id)
			findNavController().navigate(R.id.action_nav_home_to_nav_user_details)
		}
	}

	private fun navigateToAddUser() {
		rvUsers.isLayoutFrozen = true
		toAddUserAnimation.start {
			findNavController().navigate(R.id.action_nav_home_to_nav_add_user)
		}
	}

	private fun removeUser(position: Int): Boolean {
		tmpPos = position
		viewModel.startRemoveUser(position)
		return true
	}

	private fun acceptRemoveUser() {

	}

	private fun dismissRemoveUser() {
		itemsHandler.adapter.notifyItemChanged(tmpPos)
	}

	fun onUserClick(userItem: Item, clickedView: View) = navigateToUserDetails(userItem, clickedView)

	fun onUserSwipe(position: Int) = removeUser(position)

	override fun onClick(v: View?) {
		when (v?.id) {
			R.id.fabAddUser -> navigateToAddUser()
		}
	}
}