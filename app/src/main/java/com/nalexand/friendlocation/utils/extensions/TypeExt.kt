package com.nalexand.friendlocation.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nalexand.friendlocation.base.BaseFragment
import com.nalexand.friendlocation.base.CompositeHolder
import com.nalexand.friendlocation.model.local.LocalState
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

inline fun <T : BaseFragment<*>, K> T.observe(
	data: T.() -> LiveData<K>,
	crossinline block: T.(K) -> Unit
) {
	try {
		data().observe(this as LifecycleOwner, Observer { block(it) })
	} catch (e: Throwable) {
		e.printStackTrace()
	}
}

inline fun <T : BaseFragment<*>, K> T.observe(data: LiveData<K>, crossinline block: T.(K) -> Unit) {
	try {
		data.observe(this as LifecycleOwner, Observer { block(it) })
	} catch (e: Throwable) {
		e.printStackTrace()
	}
}

inline fun <T : BaseFragment<*>> T.observeState(
	state: T.() -> LocalState,
	crossinline block: T.(Boolean) -> Unit
) {
	state().observe(this as LifecycleOwner, Observer { block(it) })
}

inline fun <T : BaseFragment<*>> T.observeState(
	state: LocalState,
	crossinline block: T.(Boolean) -> Unit
) {
	state.observe(this as LifecycleOwner, Observer { block(it) })
}

inline fun <T : CompositeHolder, K> T.subscribe(
	ps: T.() -> PublishSubject<K>,
	crossinline block: T.(K) -> Unit
) {
	ps().subscribe { block(it) }.addTo(getComposite())
}

inline fun <T : CompositeHolder, K> T.subscribe(
	ps: PublishSubject<K>,
	crossinline block: T.(K) -> Unit
) {
	ps.subscribe { block(it) }.addTo(getComposite())
}
