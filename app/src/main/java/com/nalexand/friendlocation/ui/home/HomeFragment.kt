package com.nalexand.friendlocation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
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
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<HomeViewModel>(HomeViewModel::class.java, R.layout.fragment_home),
	View.OnClickListener {

	private lateinit var itemsHandler: UserItemsHandler

	private lateinit var toAddUserAnimation: ToAddUserAnimation
	private lateinit var toUserDetailsAnimation: ToUserDetailsAnimation

	private var tmpIdUser: String? = null

	override fun initializeUi() {
		initializeAnimations()
		initializeRecycler()
		fabAddUser.setOnClickListener(this)
		srlUsers.setOnRefreshListener {
			viewModel.refreshing.start()
			viewModel.updateLocations()
		}
	}

	override fun onCreateViewModel(savedInstanceState: Bundle?) {
		super.onCreateViewModel(savedInstanceState)
		if (savedInstanceState == null) {
			viewModel.getUsers()
		}
	}

	override fun initializeObservers() {
		viewModel.errors.subscribe { errorCode ->
			if (errorCode == ERROR_NETWORK) {
				showSnackbar(clHomeContent, R.string.network_error)
			}
		}.addTo(getComposite())
		viewModel.refreshing.observe(viewLifecycleOwner, Observer { updating ->
			srlUsers.isRefreshing = updating
		})
		viewModel.users.observe(viewLifecycleOwner, Observer { users ->
			if (viewModel.refreshing.isNotActive()) {
				itemsHandler render users
			}
		})
	}

	private fun initializeRecycler() {
		itemsHandler = UserItemsHandler(this)
		val swipeDrawer = SwipeDrawer()
		val swipeController = SwipeController(mainActivity.applicationContext, itemsHandler, swipeDrawer)
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
		if (!rvUsers.isLayoutFrozen) {
			rvUsers.isLayoutFrozen = true
			viewModel.onNavigateToUserDetails(userItem.id)
			toUserDetailsAnimation.startWith(clickedView) {
				findNavController().navigate(R.id.action_nav_home_to_nav_user_details)
			}
		}
	}

	private fun navigateToAddUser() {
		if (!rvUsers.isLayoutFrozen) {
			rvUsers.isLayoutFrozen = true
			dismissRemoveUser()
			toAddUserAnimation.start {
				findNavController().navigate(R.id.action_nav_home_to_nav_add_user)
			}
		}
	}

	fun acceptRemoveUser() {
		if (tmpIdUser != null) {
			viewModel.removeUser(tmpIdUser!!)
			showSnackbar(clHomeContent, R.string.user_removed)
			tmpIdUser = null
		}
	}

	fun dismissRemoveUser() {
		if (tmpIdUser != null) {
			itemsHandler.cancelRemove(tmpIdUser!!)
			tmpIdUser = null
		}
	}

	fun onItemSwipe(idUser: String): Boolean {
		val swipePossible = !rvUsers.isLayoutFrozen
		if (swipePossible) {
			tmpIdUser = idUser
		}
		return swipePossible
	}

	fun onUserClick(userItem: Item, clickedView: View) {
		if (rvUsers.isLayoutFrozen) return
		navigateToUserDetails(userItem, clickedView)
	}

	override fun onClick(v: View?) {
		when (v?.id) {
			R.id.fabAddUser -> navigateToAddUser()
		}
	}
}