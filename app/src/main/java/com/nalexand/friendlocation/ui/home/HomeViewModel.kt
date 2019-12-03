package com.nalexand.friendlocation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.RoomDatabase
import com.nalexand.friendlocation.base.BaseViewModel
import com.nalexand.friendlocation.data.AppDatabase
import com.nalexand.friendlocation.model.local.Member
import com.nalexand.friendlocation.model.local.Token
import com.nalexand.friendlocation.repository.AppPreferences
import com.nalexand.friendlocation.repository.IntraRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
	private val repository: IntraRepository
): BaseViewModel() {

	private val _members = MutableLiveData<List<Member>>()
	val members: LiveData<List<Member>> = _members

	override fun onViewCreated() {
		_members.value = repository.getMembers()
		updateLocations()
		validateToken()
	}

	private fun updateLocations() {
		repository.updateLocations() {
			
		}
	}

	private fun validateToken() {
		val token = preferences.getToken()
		val needUpdateToken = checkTokenExpiration(token)

		if (needUpdateToken) {

		}
	}

	private fun checkTokenExpiration(token: Token): Boolean {
		return true
	}
}