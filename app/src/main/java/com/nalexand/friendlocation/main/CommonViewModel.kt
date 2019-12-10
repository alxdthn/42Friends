package com.nalexand.friendlocation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nalexand.friendlocation.base.BaseViewModel
import com.nalexand.friendlocation.model.common.State
import javax.inject.Inject

class CommonViewModel @Inject constructor(): BaseViewModel() {

	private val _sharedData = MutableLiveData<Any?>()
	val sharedData: LiveData<Any?> = _sharedData

	var appState: State = State.START

	override fun initStartData() {}
}