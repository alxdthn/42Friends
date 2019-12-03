package com.nalexand.friendlocation.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

	private var ready = false

	val composite = CompositeDisposable()

	abstract fun onViewCreated()

	override fun onCleared() {
		super.onCleared()
		composite.dispose()
	}
}