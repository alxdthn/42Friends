package com.nalexand.friendlocation.ui.home

import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.base.BaseFragment
import com.nalexand.friendlocation.model.recycler.Item
import com.nalexand.friendlocation.ui.home.adapter.UserAdapter
import com.nalexand.friendlocation.ui.home.adapter.UserItemsHandler
import com.nalexand.friendlocation.ui.home.animations.ToAddUserAnimation
import com.nalexand.friendlocation.utils.AppConstants.ERROR_NETWORK
import com.nalexand.friendlocation.utils.animator.AnimParams
import com.nalexand.friendlocation.utils.animator.AnimType.HIDE_BOTTOM
import com.nalexand.friendlocation.utils.animator.AnimType.HIDE_TOP
import com.nalexand.friendlocation.utils.animator.AnimType.SCALE_XY
import com.nalexand.friendlocation.utils.animator.animatePos
import com.nalexand.friendlocation.utils.animator.animateScale
import com.nalexand.friendlocation.utils.extensions.*
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<HomeViewModel>(R.layout.fragment_home),
	View.OnClickListener {

	lateinit var itemsHandler: UserItemsHandler

	private val toAddUserAnimation = ToAddUserAnimation(composite)

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

	fun onUserClick(userItem: Item, view: View) {
		val viewPos = rvUsers.getChildAdapterPosition(view)
		val viewAbove = prepareViews(0, viewPos - 1, rvUsers, HIDE_TOP)
		val viewBelow = prepareViews(viewPos + 1, rvUsers.itemCount(), rvUsers, HIDE_BOTTOM)
		val scale = 0.85f
		val superAnim =
			Observable.merge(Observable.fromIterable(viewAbove), Observable.fromIterable(viewBelow))
				.subscribe { params ->
					params.view.animateScale(400, scale, SCALE_XY)
						.andThen(params.view.animatePos(400, HIDE_TOP))
						.subscribe().addTo(composite)
				}.addTo(composite)

		/*	viewAbove.intervalAnim(2000) { params ->
				params.run {
					this.view.translatePos(duration, type)
						.subscribe().addTo(composite)
				}
			}.subscribe().addTo(composite)
			viewBelow.intervalAnim(2000) { params ->
				params.run {
					this.view.translatePos(duration, type)
						.subscribe().addTo(composite)
				}
			}.subscribe().addTo(composite)*/
	}

	private fun prepareViews(
		start: Int,
		end: Int,
		recycler: RecyclerView,
		type: Int
	): List<AnimParams> {
		val viewsForAnimation = mutableListOf<AnimParams>()

		for (pos in start..end) {
			val holder = recycler.findViewHolderForAdapterPosition(pos)
			if (holder is UserAdapter.ViewHolder) {
				val params = AnimParams(
					view = holder.itemView,
					duration = 5000,
					type = type,
					pos = pos
				)
				viewsForAnimation.add(params)
			}
		}
		return viewsForAnimation
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