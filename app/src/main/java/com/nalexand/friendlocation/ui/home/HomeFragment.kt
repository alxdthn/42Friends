package com.nalexand.friendlocation.ui.home

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.base.BaseFragment
import com.nalexand.friendlocation.ui.home.adapter.UserAdapter
import com.nalexand.friendlocation.ui.home.adapter.UserItemsHandler
import com.nalexand.friendlocation.utils.animator.AnimParams
import com.nalexand.friendlocation.utils.animator.AnimType.HIDE_LEFT
import com.nalexand.friendlocation.utils.animator.AnimType.HIDE_RIGHT
import com.nalexand.friendlocation.utils.animator.translatePos
import com.nalexand.friendlocation.utils.extensions.getWidth
import com.nalexand.friendlocation.utils.extensions.itemCount
import com.nalexand.friendlocation.utils.extensions.observe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_add_user.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.concurrent.TimeUnit

class HomeFragment : BaseFragment<HomeViewModel>(R.layout.fragment_home),
	View.OnClickListener {

	lateinit var itemsHandler: UserItemsHandler

	override fun initializeUi() {
		itemsHandler = UserItemsHandler(this, UserAdapter())
		rvUsers.adapter = itemsHandler.adapter
		fabAddUser.setOnClickListener(this)
	}

	override fun initializeObservers() {
		observe(viewModel.users) { users ->
			itemsHandler render users
		}
	}

	override fun onClick(v: View?) {
		when (v?.id) {
			R.id.fabAddUser -> {
				prepareAnimForNavigateToAddNewUser {
					findNavController().navigate(R.id.action_nav_home_to_nav_add_user)
				}
			}
		}
	}

	private fun prepareAnimForNavigateToAddNewUser(onAnimationComplete: () -> Unit) {
		fabAddUser.translatePos(300, HIDE_RIGHT).mergeWith {
			val size = rvUsers.itemCount()
			if (size > 0) {
				animate(prepareParams(rvUsers, size), onAnimationComplete)
			} else {
				onAnimationComplete()
			}
			it.onComplete()
		}.subscribe()
	}

	private fun animate(viewsForAnimation: List<AnimParams>, onComplete: () -> Unit) {
		val interval = Observable.interval(100, TimeUnit.MILLISECONDS)
		val viewsObservable = Observable.fromIterable(viewsForAnimation)

		Observable.zip(interval, viewsObservable,
			BiFunction<Long, AnimParams, Disposable> { _, params ->
				params.run {
					view.translatePos(duration, type)
						.subscribeOn(AndroidSchedulers.mainThread())
						.subscribe {
							if (last) onComplete()
						}
				}
			})
			.subscribe().addTo(composite)
	}

	private fun prepareParams(recycler: RecyclerView, size: Int): List<AnimParams> {
		val viewsForAnimation = mutableListOf<AnimParams>()

		for (pos in 0..size) {
			val holder = recycler.findViewHolderForAdapterPosition(pos)
			if (holder is UserAdapter.ViewHolder) {
				val even = pos % 2 == 0
				val params = AnimParams(
					view = holder.itemView,
					duration = (500 - pos * 50).toLong(),
					type = if (even) HIDE_LEFT else HIDE_RIGHT
				)
				viewsForAnimation.add(params)
			}
		}
		viewsForAnimation.last().last = true
		return viewsForAnimation
	}
}