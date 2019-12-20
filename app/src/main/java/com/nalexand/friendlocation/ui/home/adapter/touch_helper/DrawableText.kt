package com.nalexand.friendlocation.ui.home.adapter.touch_helper

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import kotlin.math.absoluteValue

class DrawableText(
	val label: String,
	val paint: TextPaint
) {

	private var onClick: (() -> Unit)? = null

	var left: Float = 0f
	var top: Float = 0f

	val padding = 10f
	val rect: Rect = Rect()
	val tmpRect: Rect = Rect()
	val boundPaint: Paint = Paint()
	val boundTextPaint: TextPaint = TextPaint(TextPaint.ANTI_ALIAS_FLAG)

	init {
		boundTextPaint.apply {
			textSize = 20f
			color = Color.WHITE
		}
		boundPaint.apply {
			strokeWidth = 1f
			style = Paint.Style.STROKE
			color = Color.WHITE
		}
	}

	fun height() = rect.height()

	fun width() = rect.width()

	fun draw(canvas: Canvas?, printBounds: Boolean = false) {
		canvas?.apply {
			drawText(label, left, top, paint)
			if (printBounds) {
				drawBoundsWithInfo(canvas)
			}
		}
	}

	private fun drawBoundsWithInfo(canvas: Canvas) = canvas.apply {
		val bottom = top
		val top = top - height()
		drawRect(left, top, left + width(), bottom, boundPaint)
		drawText(	// TOP
			getBoundInfo(top),
			left + width() / 2f - tmpRect.centerX(),
			top - padding,
			boundTextPaint
		)
		drawText(	// LEFT
			getBoundInfo(left),
			left - tmpRect.width() - padding,
			top + height() + tmpRect.centerY(),
			boundTextPaint
		)
		drawText(	// RIGHT
			getBoundInfo(left + tmpRect.width()),
			left + width() + padding,
			top + height() + tmpRect.centerY(),
			boundTextPaint
		)
		drawText(	// BOTTOM
			getBoundInfo(top + tmpRect.height()),
			left + width() / 2f - tmpRect.centerX(),
			top + height() + tmpRect.height() + padding,
			boundTextPaint
		)
	}

	private fun getBoundInfo(value: Float): String {
		val string = value.toInt().toString()
		boundTextPaint.getTextBounds(string, 0, string.length, tmpRect)
		return string
	}

	private fun getBoundInfo(value: Int): String {
		val string = value.toString()
		boundTextPaint.getTextBounds(string, 0, string.length, tmpRect)
		return string
	}

	fun calcTop(center: Float) {
		top = center + rect.centerY().absoluteValue
	}

	fun setOnClickListener(onClick: () -> Unit) {
		this.onClick = onClick
	}

	fun setTextBounds() {
		paint.getTextBounds(label, 0, label.length, rect)
	}

	fun onClick(x: Float, y: Float): Boolean {
		val containsClick = contains(x, y)
		if (containsClick) onClick?.invoke()
		return containsClick
	}

	private fun contains(otherX: Float, otherY: Float): Boolean {
		return left < otherX
				&& otherX < left + width()
				&& top - height() < otherY
				&& otherY < top
	}
}