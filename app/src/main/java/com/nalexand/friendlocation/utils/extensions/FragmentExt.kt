package com.nalexand.friendlocation.utils.extensions

import android.content.Context
import android.content.res.Configuration
import android.os.Handler
import android.util.TypedValue
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.fragment.app.Fragment
import com.nalexand.friendlocation.R

fun Fragment.getOrientation(): Int {
	return activity!!.resources.configuration.orientation
}

fun Fragment.orientationPortrait(): Boolean {
	return getOrientation() == Configuration.ORIENTATION_PORTRAIT
}

fun Fragment.getAppBarHeight(): Int {
	val tv = TypedValue()

	return if (context!!.theme.resolveAttribute(R.attr.actionBarSize, tv, true)) {
		TypedValue.complexToDimensionPixelSize(
			tv.data,
			context!!.resources.displayMetrics
		)
	} else -1
}

infix fun Fragment.showToastBy(@StringRes resId: Int) {
	Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToastBy(message: String) {
	Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showPopUpMenu(view: View, layout: Int, callback: MenuBuilder.Callback) {
	val menuBuilder = MenuBuilder(context)
	val inflater = MenuInflater(context)
	val menu = MenuPopupHelper(context!!, menuBuilder, view)

	inflater.inflate(layout, menuBuilder)
	menu.setForceShowIcon(true)
	menuBuilder.setCallback(callback)
	menu.show()
}

fun Fragment.hideKeyboard() {
	val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	val token = activity?.window?.decorView?.windowToken

	Handler().post {
		imm.hideSoftInputFromWindow(token, 0)
	}
}