package com.nalexand.friendlocation.base

import android.util.Log
import androidx.lifecycle.ViewModel
import com.nalexand.friendlocation.main.CommonViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel(), CompositeHolder {

	private var ready = false

	private val compositeDisposable = CompositeDisposable()

	lateinit var commonViewModel: CommonViewModel

	abstract fun initStartData()

	fun init() {
		if (!ready) {
			printInitLog()
			initStartData()
			ready = true
		}
	}

	override fun getComposite() = compositeDisposable

	override fun onCleared() {
		super.onCleared()
		compositeDisposable.dispose()
	}

	private fun printInitLog() = Unit
}