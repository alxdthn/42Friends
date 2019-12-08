package com.nalexand.friendlocation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nalexand.friendlocation.base.BaseViewModel
import com.nalexand.friendlocation.model.local.User
import com.nalexand.friendlocation.repository.IntraRepository
import com.nalexand.friendlocation.utils.extensions.observeState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class HomeViewModel @Inject constructor(
	private val repository: IntraRepository
) : BaseViewModel() {

	private val _users = MutableLiveData<List<User>>()
	val users: LiveData<List<User>> = _users

	override fun initStartData() {
		updateLocations()
	}

	private fun updateLocations() {
		repository.updateLocations()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({ users ->
				_users.value = users
			}) { error ->
				Log.d("bestTAG", error.message.toString())
			}.addTo(composite)
		}
}