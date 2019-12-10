package com.nalexand.friendlocation.main

import com.nalexand.friendlocation.base.BaseViewModel
import com.nalexand.friendlocation.model.common.State
import javax.inject.Inject

class CommonViewModel @Inject constructor(): BaseViewModel() {

	var sharedData: Any? = null

	var appState: State = State.START

	override fun initStartData() {}
}