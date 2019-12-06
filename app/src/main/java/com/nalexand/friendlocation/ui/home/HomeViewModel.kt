package com.nalexand.friendlocation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nalexand.friendlocation.base.BaseViewModel
import com.nalexand.friendlocation.model.local.User
import com.nalexand.friendlocation.repository.IntraRepository
import com.nalexand.friendlocation.utils.extensions.subscribe
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class HomeViewModel @Inject constructor(
	private val repository: IntraRepository
) : BaseViewModel() {

	private val _users = MutableLiveData<List<User>>()
	val users: LiveData<List<User>> = _users

	override fun onViewCreated() {
		_users.value = repository.getAllUsersFromDatabase()
		updateLocations()
	}

	private fun updateLocations() {
	}
}