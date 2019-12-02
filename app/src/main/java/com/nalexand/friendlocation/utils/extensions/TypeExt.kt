package com.nalexand.friendlocation.utils.extensions

/*
inline fun <T, K> T.observe(data: T.() -> LiveData<K>, crossinline block: T.(K) -> Unit) {
	try {
		data().observe(this as LifecycleOwner, Observer { block(it) })
	} catch (e: Throwable) {
		e.printStackTrace()
	}
}

inline fun <T> T.observeState(state: T.() -> LocalState, crossinline block: T.(Boolean) -> Unit) {
	state().observe(this as LifecycleOwner, Observer { block(it) })
}

inline fun <T, K> T.subscribe(ps: T.() -> PublishSubject<K>, crossinline block: T.(K) -> Unit) {
	val composite = when (this) {
		is BaseFragment -> (this as BaseFragment).composite
		is BaseViewModel -> (this as BaseViewModel).composite
		else -> throw IllegalArgumentException()
	}
	ps().subscribe { block(it) }.addTo(composite)
}
*/