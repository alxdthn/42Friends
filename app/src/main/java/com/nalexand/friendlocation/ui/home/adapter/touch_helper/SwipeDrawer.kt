package com.nalexand.friendlocation.ui.home.adapter.touch_helper

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.text.TextPaint
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.nalexand.friendlocation.R
import kotlin.math.absoluteValue

class SwipeDrawer(appContext: Context) {

	private val blurSize = 10f

	private val messagePaint = TextPaint(TextPaint.ANTI_ALIAS_FLAG).apply {
		color = ContextCompat.getColor(appContext, R.color.white80)
		typeface = ResourcesCompat.getFont(appContext, R.font.nunito_light)
		textSize = appContext.resources.getDimensionPixelOffset(R.dimen.remove_user_message).toFloat()
		setShadowLayer(blurSize, 0f, 0f, color)
	}

	private val buttonPaint = TextPaint(TextPaint.ANTI_ALIAS_FLAG).apply {
		color = messagePaint.color
		typeface = messagePaint.typeface
		textSize = appContext.resources.getDimensionPixelSize(R.dimen.remove_user_button).toFloat()
		setShadowLayer(blurSize, 0f,0f, color)
	}

	private val maxAlpha = 255

	private var absX = 0f
	private var centerY = 0f
	private var drawLeft = false
	private var currentThreshold = 0f
	private var canvas: Canvas? = null
	private var itemWidth = 0
	private var itemHeight = 0

	private val messageRect = Rect()
	private val messageText = appContext.getString(R.string.remove_user)
	private val messageAlphaStart = 0.3f
	private val messageAlphaFull = 0.6f

	private val buttonRect = Rect()
	private val buttonAcceptText = "YES"
	private val buttonDismissText = "NO"
	private val buttonAlphaStart = 0.9f
	private val buttonAlphaFull = 1.0f

	fun setParams(c: Canvas, top: Int, dX: Float, itemHeight: Int, itemWidth: Int) {
		this.itemWidth = itemWidth
		this.itemHeight = itemHeight
		centerY = top + itemHeight / 2f
		currentThreshold = dX.absoluteValue / itemWidth
		absX = dX.absoluteValue
		canvas = c
		drawLeft = dX >= 0
	}

	fun drawButtons(): Boolean {
		buttonPaint.alpha = calculateAlpha(buttonAlphaStart, buttonAlphaFull)
		val top = centerY + buttonRect.centerY().absoluteValue
		drawAcceptButton(top)
		drawDismissButton(top)
		return currentThreshold > buttonAlphaStart
	}

	private fun drawDismissButton(top: Float) {
		buttonPaint.getTextBounds(buttonDismissText, 0, buttonDismissText.length, buttonRect)
		val left = itemHeight / 2f - buttonRect.centerX()
		canvas?.drawText(buttonDismissText, left, top, buttonPaint)
	}

	private fun drawAcceptButton(top: Float) {
		buttonPaint.getTextBounds(buttonAcceptText, 0, buttonAcceptText.length, buttonRect)
		val left = itemWidth - itemHeight + buttonRect.centerX()
		canvas?.drawText(buttonAcceptText, left.toFloat(), top, buttonPaint)
	}

	fun drawMessage() {
		messagePaint.alpha = calculateAlpha(messageAlphaStart, messageAlphaFull)
		messagePaint.getTextBounds(messageText, 0, messageText.length, messageRect)

		val halfX = absX / 2f
		val left = (if (drawLeft) halfX else itemWidth - halfX) - messageRect.centerX()
		val top = centerY + messageRect.centerY().absoluteValue

		canvas?.drawText(messageText, left, top, messagePaint)
	}

	private fun calculateAlpha(
		startThreshold: Float,
		fullThreshold: Float
	): Int {
		return when {
			currentThreshold >= fullThreshold -> maxAlpha
			currentThreshold >= startThreshold -> {
				(maxAlpha * ((currentThreshold - startThreshold)
						/ (fullThreshold - startThreshold)) + 0.5).toInt()
			}
			else -> 0
		}
	}
}