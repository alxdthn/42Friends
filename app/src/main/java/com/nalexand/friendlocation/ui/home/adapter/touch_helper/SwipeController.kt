package com.nalexand.friendlocation.ui.home.adapter.touch_helper

import android.content.Context
import android.graphics.Canvas
import android.text.TextPaint
import android.util.Log
import android.view.MotionEvent
import android.view.SoundEffectConstants
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.RecyclerView
import com.nalexand.friendlocation.R

class SwipeController(
	appContext: Context,
	private val listener: TouchHelperInterface,
	private val drawer: SwipeDrawer
) : (Canvas, RecyclerView, RecyclerView.ViewHolder, Float, Float, Int, Boolean) -> Unit,
	View.OnTouchListener {

	private var itemWidth = 0
	private var itemHeight = 0
	private var itemTop = 0

	private var buttonsVisible = false

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

	private val message = DrawableText(appContext.getString(R.string.remove_user), messagePaint).apply {
		setTextBounds()
	}

	private val acceptButton = DrawableText("YES", buttonPaint).apply {
		setOnClickListener(listener::onClickRight)
		setTextBounds()
	}

	private val dismissButton = DrawableText("NO", buttonPaint).apply {
		setOnClickListener(listener::onClickLeft)
		setTextBounds()
	}

	override fun invoke(
		c: Canvas,
		recyclerView: RecyclerView,
		viewHolder: RecyclerView.ViewHolder,
		dX: Float,
		dY: Float,
		actionState: Int,
		isCurrentlyActive: Boolean
	) {
		if (actionState == ACTION_STATE_SWIPE) {
			val itemView = viewHolder.itemView

			itemHeight = itemView.bottom - itemView.top
			itemWidth = itemView.right - itemView.left
			itemTop = itemView.top
			buttonsVisible = drawContent(c, itemView.top, dX)
		}
	}

	override fun onTouch(v: View, event: MotionEvent): Boolean {
		var clicked = false
		if (buttonsVisible && event.action == MotionEvent.ACTION_UP) {
			clicked = acceptButton.onClick(event.x, event.y)
					|| dismissButton.onClick(event.x, event.y)
			if (clicked) v.playSoundEffect(SoundEffectConstants.CLICK)
		}
		return clicked
	}

	private fun drawContent(c: Canvas, top: Int, dX: Float): Boolean {
		return drawer.run {
			setParams(c, top, dX, itemHeight, itemWidth)
			drawMessage(message)
			drawButtons(message, acceptButton, dismissButton)
		}
	}
}