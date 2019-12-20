package com.nalexand.friendlocation.ui.home.adapter.touch_helper

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import kotlin.math.absoluteValue

class SwipeDrawer {

	private val maxAlpha = 255
	private val printBounds = false

	private var absX = 0f
	private var centerY = 0f
	private var drawLeft = false
	private var currentThreshold = 0f
	private var canvas: Canvas? = null
	private var itemWidth = 0
	private var itemHeight = 0

	private val messageAlphaStart = 0.3f
	private val messageAlphaFull = 0.6f

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

	fun drawButtons(
		message: DrawableText,
		acceptButton: DrawableText,
		dismissButton: DrawableText
	): Boolean {
		val currentAlpha = calculateAlpha(buttonAlphaStart, buttonAlphaFull)
		dismissButton.paint.alpha = currentAlpha
		acceptButton.paint.alpha = currentAlpha
		drawAcceptButton(message, acceptButton)
		drawDismissButton(message, dismissButton)
		return currentThreshold > buttonAlphaStart
	}

	private fun drawDismissButton(message: DrawableText, button: DrawableText) = button.apply {
		left = message.left / 2f - rect.centerX()
		calcTop(centerY)
		draw(canvas, printBounds)
	}

	private fun drawAcceptButton(message: DrawableText, button: DrawableText) = button.apply {
		left = itemWidth - ((itemWidth - (message.left + message.width())) / 2f + rect.centerX())
		calcTop(centerY)
		draw(canvas, printBounds)
	}

	fun drawMessage(message: DrawableText) = message.apply {
		paint.alpha = calculateAlpha(messageAlphaStart, messageAlphaFull)
		left = (if (drawLeft) absX / 2f else itemWidth - absX / 2f) - rect.centerX()
		calcTop(centerY)
		draw(canvas, printBounds)
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