package com.nalexand.friendlocation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.utils.animator.AnimType
import com.nalexand.friendlocation.utils.animator.animatePos
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class RotateView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle) {

	private val composite = CompositeDisposable()
	private val payload = mutableListOf<View>()
	private val layout = R.layout.recycler_user_item
	private val winWidth: Float

	init {
		val metrics = context.resources.displayMetrics
		winWidth = metrics.widthPixels.toFloat()

		for (x in 1..2) {
			val view = LayoutInflater.from(context).inflate(layout, this, false)
			view.translationX = winWidth
			addView(view)
			payload.add(view)
		}
	}

	fun bindAndStart(bind: (View) -> Unit) {
		val view = getActual()
		bind(view)
		moveView()
	}

	override fun onViewRemoved(child: View?) {
		composite.dispose()
	}

	private fun moveView() {
		if (payload[0].translationX == payload[1].translationX) {
			payload[0].animatePos(500, AnimType.SHOW_RIGHT).subscribe()
		} else {
			Observable.fromIterable(payload).subscribe { view ->
				if (view.translationX == 0f) {
					view.animatePos(500, AnimType.HIDE_LEFT)
						.subscribe {
							view.translationX = winWidth
						}
				} else {
					view.animatePos(500, AnimType.SHOW_RIGHT).subscribe()
				}
			}.addTo(composite)
		}
	}

	private fun getActual(): View {
		return if (payload[0].translationX == payload[1].translationX) {
			payload[0]
		} else {
			payload.find { it.translationX == winWidth } ?: throw IllegalStateException()
		}
	}
}