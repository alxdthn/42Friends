package com.nalexand.friendlocation.base

import io.reactivex.disposables.CompositeDisposable

interface CompositeHolder {
	fun getComposite(): CompositeDisposable
}