package com.nalexand.friendlocation.utils

import android.view.View
import com.nalexand.friendlocation.model.recycler.Item
import com.nalexand.friendlocation.model.recycler.UserItem
import kotlinx.android.synthetic.main.recycler_user_item.view.*

object UserBinder {

	private var enabledColor = 0
	private var disabledColor = 0

	private var enabledShadowRad = 10f

	fun init(enabledColor: Int, disabledColor: Int, enabledShadowRad: Float) {
		UserBinder.enabledColor = enabledColor
		UserBinder.disabledColor = disabledColor
		UserBinder.enabledShadowRad = enabledShadowRad
	}

	fun bindTo(view: View, item: UserItem, onClick: ((Item, View) -> Unit)? = null) {
		bindLogin(view, item.name)
		bindHost(view, item.host)
		bindColor(
			view,
			item.color,
			item.shadow
		)
		if (onClick != null) {
			view.setOnClickListener {
				onClick(item, it)
			}
		}
	}

	fun bindTo(view: View, payloads: MutableList<Any?>) {

	}

	private fun bindColor(view: View, color: Int, shadow: Float) {
		view.apply {
			txvUserHost.setTextColor(color)
			txvUserLogin.setTextColor(color)
			txvUserHost.setShadowLayer(shadow, 0f, 0f, color)
			txvUserLogin.setShadowLayer(shadow, 0f, 0f, color)
		}
	}

	private fun bindLogin(view: View, login: String) {
		view.txvUserLogin.text = login
	}

	private fun bindHost(view: View, host: String) {
		view.txvUserHost.text = host
	}



	fun getParams(host: String?) = if (host == null) {
		UserItem.Params(disabledColor, 0f)
	} else {
		UserItem.Params(
			enabledColor,
			enabledShadowRad
		)
	}
}