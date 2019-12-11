package com.nalexand.friendlocation.ui.home.adapter.touch_helper

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.nalexand.friendlocation.R
import kotlinx.android.synthetic.main.user_item.view.*
import kotlin.math.absoluteValue

class OnSwipeDrawer(appContext: Context) {

	private val messagePaint = TextPaint(TextPaint.ANTI_ALIAS_FLAG).apply {
		color = ContextCompat.getColor(appContext, R.color.white80)
		typeface = ResourcesCompat.getFont(appContext, R.font.nunito_light)
		textSize = appContext.resources.getDimensionPixelOffset(R.dimen.remove_user_message).toFloat()
		setShadowLayer(10f, 0f, 0f, color)
	}

	private val messageRect = Rect()
	private val messageText = appContext.getString(R.string.remove_user)
	private val messageAlphaStartThreshold = 0.3
	private val messageAlphaFullThreshold = 0.6
	private val messageAlphaThresholdLen = messageAlphaFullThreshold - messageAlphaStartThreshold

	fun draw(
		viewHolder: RecyclerView.ViewHolder,
		canvas: Canvas,
		dX: Float
	) {
		val absX = dX.absoluteValue
		val itemView = viewHolder.itemView
		val itemHeight = itemView.bottom - itemView.top
		val itemWidth = itemView.right - itemView.left
		val currentThreshold = absX / itemWidth

		messagePaint.alpha = calculateAlpha(currentThreshold)
		messagePaint.getTextBounds(messageText, 0, messageText.length, messageRect)

		val messageLeft = if (dX > 0) {
			dX / 2f - messageRect.centerX()
		} else {
			itemWidth - absX + absX / 2 - messageRect.centerX()
		}
		val messageTop = itemView.top + (itemHeight) / 2f + messageRect.centerY().absoluteValue

		canvas.drawText(messageText, messageLeft, messageTop, messagePaint)
	}

	private fun calculateAlpha(currentThreshold: Float): Int {
		return when {
			currentThreshold >= messageAlphaFullThreshold -> 255
			currentThreshold >= messageAlphaStartThreshold -> {
				(255 * (currentThreshold - messageAlphaStartThreshold) /
						messageAlphaThresholdLen + 0.5).toInt()
			}
			else -> 0
		}
	}
}