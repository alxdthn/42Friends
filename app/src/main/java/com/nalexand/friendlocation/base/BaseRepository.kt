package com.nalexand.friendlocation.base

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers

abstract class BaseRepository {

	fun <T> prepareFlowable(flowable: Flowable<T>): Flowable<T> {
		return flowable
			.observeOn(AndroidSchedulers.mainThread())
			.cache()
	}
}