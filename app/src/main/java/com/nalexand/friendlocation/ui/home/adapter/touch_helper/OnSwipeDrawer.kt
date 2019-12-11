package com.nalexand.friendlocation.ui.home.adapter.touch_helper

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.nalexand.friendlocation.R
import kotlin.math.absoluteValue

class OnSwipeDrawer(appContext: Context) {

	private val messagePaint = Paint().apply {
		color = Color.WHITE
		typeface = ResourcesCompat.getFont(appContext, R.font.nunito_light)
	}

	private val messageRect = Rect()

	private val messageText = "Remove user?"

	private var messageSize = 20f

	private val messageAlphaStartThreshold = 0.2

	private val messageAlphaFullThreshold = 0.5

	private val messageAlphaThresholdLen = messageAlphaFullThreshold - messageAlphaStartThreshold

	fun draw(
		viewHolder: RecyclerView.ViewHolder,
		canvas: Canvas,
		dX: Float,
		isCurrentlyActive: Boolean
	) {
		val absX = dX.absoluteValue
		val itemView = viewHolder.itemView
		val itemHeight = itemView.bottom - itemView.top
		val itemWidth = itemView.right - itemView.left

		messagePaint.alpha = calculateAlpha(absX, itemWidth)
		messageSize = (absX * 0.07f)
		messagePaint.textSize = messageSize
		messagePaint.getTextBounds(messageText, 0, messageText.length, messageRect)

		val messageLeft = if (dX > 0) {
			dX / 2f - messageRect.centerX()
		} else {
			itemWidth - absX + absX / 2 - messageRect.centerX()
		}
		val messageTop = itemView.top + (itemHeight) / 2f + messageRect.centerY().absoluteValue

		canvas.drawText(messageText, messageLeft, messageTop, messagePaint)
	}

	private fun calculateAlpha(absX: Float, width: Int): Int {
		val current = absX / width
		return if (current >= messageAlphaStartThreshold) {
			(255 * (current - messageAlphaStartThreshold) / messageAlphaThresholdLen + 0.5).toInt()
		} else 0
	}
}