package com.nalexand.friendlocation.model.local

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class LocalState() {

	constructor(startValue: Boolean): this() {
		state.apply { value = startValue }
	}

	private var withLog = false
	private var name: String = "unknown state"

	private val state = MutableLiveData<Boolean>()

	fun startIfDisabled() {
		if (state.value == false || state.value == null) state.value = true
	}

	fun start() {
		if (withLog) Log.d("bestTAG", "STATE($name): start")
		state.postValue(true)
	}

	fun isActive(): Boolean {
		return state.value ?: false
	}

	fun cancel() {
		if (withLog) Log.d("bestTAG", "STATE($name): cancel")
		state.postValue(false)
	}

	fun cancelIfActive() {
		if (isActive()) cancel()
	}

	fun observe(owner: LifecycleOwner, observer: Observer<Boolean>) {
		state.observe(owner, observer)
	}

	fun toggle() {
		if (isActive()) cancel() else start()
	}

	fun set(value: Boolean) {
		state.value = value
	}

	fun withLog() {
		withLog = true
	}

	fun withLog(name: String): LocalState {
		withLog = true
		this.name = name
		return this
	}
}