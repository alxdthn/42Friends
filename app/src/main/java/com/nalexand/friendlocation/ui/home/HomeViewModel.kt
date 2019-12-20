package com.nalexand.friendlocation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nalexand.friendlocation.base.BaseViewModel
import com.nalexand.friendlocation.model.common.State
import com.nalexand.friendlocation.model.local.LocalState
import com.nalexand.friendlocation.model.local.User
import com.nalexand.friendlocation.repository.IntraRepository
import com.nalexand.friendlocation.utils.AppConstants.ERROR_NETWORK
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class HomeViewModel @Inject constructor(
	private val repository: IntraRepository
) : BaseViewModel() {

	private val _users = MutableLiveData<List<User>>()
	val users: LiveData<List<User>> = _users

	val errors = PublishSubject.create<Int>()

	val refreshing = LocalState()

	override fun initStartData() {
		if (commonViewModel.appState == State.START) {
			updateLocations()
		}
		commonViewModel.appState = State.HOME
	}

	fun getUsers() {
		_users.value = repository.getAllUsersFromDatabase().sorted()
	}

	fun updateLocations() {
		repository.updateLocations()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({ users ->
				refreshing.cancelIfActive()
				_users.value = users.sorted()
			}) { error ->
				refreshing.cancelIfActive()
				errors.onNext(ERROR_NETWORK)
				Log.d("bestTAG", error.message.toString())
			}.addTo(getComposite())
	}

	fun onNavigateToUserDetails(userId: String) {
		commonViewModel.sharedData = userId
	}

	fun removeUser(idUser: String) {
		_users.value = repository.removeUser(idUser).sorted()
	}

	private fun List<User>.sorted(): List<User> {
		return sortedWith(compareBy(
			{ it.host == null },
			{ it.login }
		))
	}
}