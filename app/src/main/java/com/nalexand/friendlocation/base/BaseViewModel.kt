package com.nalexand.friendlocation.base

import androidx.lifecycle.ViewModel
import com.nalexand.friendlocation.main.CommonViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

	private var ready = false

	val composite = CompositeDisposable()

	lateinit var commonViewModel: CommonViewModel

	abstract fun initStartData()

	fun init() {
		if (!ready) {
			initStartData()
			ready = true
		}
	}

	override fun onCleared() {
		super.onCleared()
		composite.dispose()
	}
}