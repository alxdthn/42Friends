package com.nalexand.friendlocation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nalexand.friendlocation.base.BaseViewModel
import javax.inject.Inject

class CommonViewModel @Inject constructor(): BaseViewModel() {

	private val _sharedData = MutableLiveData<Any?>()
	val sharedData: LiveData<Any?> = _sharedData

	override fun initStartData() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}