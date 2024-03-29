package com.nalexand.friendlocation.utils.extensions

import android.content.Context
import android.os.Handler
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar

fun View.showKeyboard() {
	val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

infix fun View.invisibleIf(invisible: Boolean) {
	visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}

infix fun View.visibleIf(visible: Boolean) {
	visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

infix fun View.goneIf(gone: Boolean) {
	visibility = if (gone) View.GONE else View.VISIBLE
}

infix fun View.hereIf(here: Boolean) {
	visibility = if (here) View.VISIBLE else View.GONE
}

fun View.switchVisible() {
	visibility = if (visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
}

fun View.switchGone() {
	visibility = if (visibility == View.VISIBLE) View.GONE else View.INVISIBLE
}

infix fun View.setPaddingBottom(padding: Int) {
	setPadding(paddingLeft, paddingTop, paddingRight, padding)
}

fun View.gone() {
	visibility = View.GONE
}

fun View.visible() {
	visibility = View.VISIBLE
}

fun View.invisible() {
	visibility = View.INVISIBLE
}

infix fun View.dp(value: Float): Int {
	return TypedValue.applyDimension(
		TypedValue.COMPLEX_UNIT_DIP, value, resources.displayMetrics
	).toInt()
}

infix fun View.dpf(value: Float): Float {
	return TypedValue.applyDimension(
		TypedValue.COMPLEX_UNIT_DIP, value, resources.displayMetrics
	)
}

fun View.showSnackbar(text: String) {
	Snackbar.make(this, text, Snackbar.LENGTH_SHORT).show()
}

fun View.showSnackbar(resId: Int) {
	val text = resources.getString(resId)
	Snackbar.make(this, text, Snackbar.LENGTH_SHORT).show()
}