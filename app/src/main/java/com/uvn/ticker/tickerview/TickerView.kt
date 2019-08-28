package com.uvn.ticker.tickerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.uvn.ticker.tickerview.TickerTextState.LEFT_OF_SCREEN

class TickerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {

    private val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private var messages = mutableListOf<TickerText>()
    private lateinit var params: TickerScreenParams
    private var halfScreenHeight = 0f
    private var screenWidth = 0f
    private var textWidth = 0f

    fun setParams(params: TickerScreenParams) {
        this.params = params
        with(paint) {
            color = params.textColor
            textSize = params.textSize
            textWidth = measureText(params.text)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        halfScreenHeight = h.toFloat() / 2
        messages.add(
            TickerText(
                textWidth,
                screenWidth,
                step = params.textSpeed
            )
        )
    }

    private fun drawText(canvas: Canvas, step: Int, paint: TextPaint, textHeight: Float) =
        canvas.drawText(params.text, screenWidth - step, halfScreenHeight + textHeight / 4, paint)

    private fun createTickerMessage() =
        TickerText(textWidth, screenWidth, step = params.textSpeed)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d("TickerView", "onDraw ${messages.size}")

        val fm = paint.fontMetrics
        val textHeight = fm.descent - fm.ascent

        messages.forEach { text ->
            drawText(canvas, text.position.toInt(), paint, textHeight)
            text.move()
        }

        if (messages.all { it.getTextState() == TickerTextState.VISIBLE }) {
            messages.add(createTickerMessage())
        }

        if (messages.first().getTextState() == LEFT_OF_SCREEN) {
            messages.removeAt(0)
        }

        postInvalidateDelayed(5)
    }
}