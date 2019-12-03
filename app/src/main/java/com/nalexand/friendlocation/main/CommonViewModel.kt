package com.nalexand.friendlocation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nalexand.friendlocation.base.BaseViewModel

class CommonViewModel : BaseViewModel() {

	private val _sharedData = MutableLiveData<Any?>()
	val sharedData: LiveData<Any?> = _sharedData

	override fun onViewCreated() {  }
}