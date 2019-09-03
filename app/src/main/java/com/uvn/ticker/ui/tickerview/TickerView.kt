package com.uvn.ticker.ui.tickerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.uvn.ticker.data.TickerParam
import com.uvn.ticker.data.TickerText
import com.uvn.ticker.data.TickerTextState

class TickerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {

    lateinit var params: TickerParam

    private val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private var messages = mutableListOf<TickerText>()
    private var halfScreenHeight = 0f
    private var screenWidth = 0f
    private var screenHeight = 0f
    private var textWidth = 0f

    fun initParams(params: TickerParam?) = params?.let {
        this.params = params
        with(paint) {
            color = params.textColor
            textWidth = measureText(params.text)
        }
    }

    fun setSpeed(step: Float) {
        messages.clear()
        params.textSpeed = step
    }

    fun setTextRatio(ratio: Float) {
        messages.clear()
        calculateWithRatio(ratio)
        params.textRatio = ratio
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()

        calculateWithRatio(params.textRatio)

        halfScreenHeight = h.toFloat() / 2
        messages.add(createTickerMessage())
    }

    private fun calculateWithRatio(ratio: Float) {
        val textSizeWithHeightRatio = (screenHeight) * ratio
        paint.textSize = textSizeWithHeightRatio
        textWidth = paint.measureText(params.text)
    }

    private fun drawText(canvas: Canvas, step: Int, paint: TextPaint, textHeight: Float) =
        canvas.drawText(params.text, screenWidth - step, halfScreenHeight + textHeight / 4, paint)

    private fun createTickerMessage() =
        TickerText(
            textWidth,
            screenWidth,
            step = params.textSpeed
        )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val fm = paint.fontMetrics
        val textHeight = fm.descent - fm.ascent

        messages.forEach { text ->
            drawText(canvas, text.position.toInt(), paint, textHeight)
            text.move()
        }

        if (messages.all { it.getTextState() == TickerTextState.VISIBLE }) {
            messages.add(createTickerMessage())
        }

        if (messages.first().getTextState() == TickerTextState.LEFT_OF_SCREEN) {
            messages.removeAt(0)
        }

        postInvalidateDelayed(5)
    }
}