package com.nalexand.friendlocation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nalexand.friendlocation.base.BaseViewModel
import com.nalexand.friendlocation.model.local.User
import com.nalexand.friendlocation.model.local.Token
import com.nalexand.friendlocation.repository.IntraRepository
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class HomeViewModel @Inject constructor(
	private val repository: IntraRepository
): BaseViewModel() {

	private val _members = MutableLiveData<List<User>>()
	val members: LiveData<List<User>> = _members

	override fun onViewCreated() {
		repository.getMembers()
			.subscribe { _members.value = it }
			.addTo(composite)
		updateLocations()
	}

	private fun updateLocations() {
	}

	private fun checkTokenExpiration(token: Token): Boolean {
		return true
	}
}