package com.nalexand.friendlocation.utils.extensions

fun <T> MutableList<T>.removeFirstIf(filter: (T) -> Boolean): Boolean {
	val each = this.iterator()

	while (each.hasNext()) {
		if (filter(each.next())) {
			each.remove()
			return true
		}
	}
	return false
}

fun <T> MutableList<T>.replaceAll(elements: Collection<T>) {
	this.clear()
	this.addAll(elements)
}