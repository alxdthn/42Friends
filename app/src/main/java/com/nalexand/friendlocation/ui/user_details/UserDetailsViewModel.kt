package com.nalexand.friendlocation.ui.user_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nalexand.friendlocation.base.BaseViewModel
import com.nalexand.friendlocation.model.local.User
import com.nalexand.friendlocation.repository.IntraRepository
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(private val repository: IntraRepository) :
	BaseViewModel() {

	private val _user = MutableLiveData<User>()
	val user: LiveData<User> = _user

	override fun initStartData() {
		val userId = commonViewModel.sharedData as String
		_user.value = repository.getUserFromDatabaseById(userId)
			?: throw IllegalArgumentException("Cant' find user")
	}
}