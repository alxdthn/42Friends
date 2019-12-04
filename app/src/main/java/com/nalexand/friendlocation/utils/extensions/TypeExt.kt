package com.nalexand.friendlocation.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.nalexand.friendlocation.base.BaseFragment
import com.nalexand.friendlocation.base.BaseViewModel
import com.nalexand.friendlocation.model.local.LocalState
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import java.lang.IllegalArgumentException

/*
inline fun <T, K> T.observe(data: T.() -> LiveData<K>, crossinline block: T.(K) -> Unit) {
	try {
		data().observe(this as LifecycleOwner, Observer { block(it) })
	} catch (e: Throwable) {
		e.printStackTrace()
	}
}
*/

inline fun <T> T.observeState(state: T.() -> LocalState, crossinline block: T.(Boolean) -> Unit) {
	state().observe(this as LifecycleOwner, Observer { block(it) })
}

inline fun <T> T.observeState(state: LocalState, crossinline block: T.(Boolean) -> Unit) {
	state.observe(this as LifecycleOwner, Observer { block(it) })
}

inline fun <T, K> T.subscribe(ps: T.() -> PublishSubject<K>, crossinline block: T.(K) -> Unit) {
	val composite = when (this) {
		is BaseViewModel -> composite
		is BaseFragment<*> -> composite
		else -> throw IllegalArgumentException()
	}
	ps().subscribe { block(it) }.addTo(composite)
}

inline fun <T, K> T.subscribe(ps: PublishSubject<K>, crossinline block: T.(K) -> Unit) {
	val composite = when (this) {
		is BaseViewModel -> composite
		is BaseFragment<*> -> composite
		else -> throw IllegalArgumentException()
	}
	ps.subscribe { block(it) }.addTo(composite)
}
