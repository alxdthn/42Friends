package com.nalexand.friendlocation.base

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseRepository {

	fun <T> prepareFlowable(flowable: Flowable<T>): Flowable<T> {
		return flowable
			.observeOn(AndroidSchedulers.mainThread())
			.cache()
	}

	fun <T> prepareSingle(single: Single<T>): Single<T> {
		return single
			.subscribeOn(Schedulers.io())
			.observeOn(Schedulers.io())
	}

}