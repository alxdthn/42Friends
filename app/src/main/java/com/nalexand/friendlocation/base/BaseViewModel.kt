package com.nalexand.friendlocation.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

	private var ready = false

	abstract fun onViewCreated()
}