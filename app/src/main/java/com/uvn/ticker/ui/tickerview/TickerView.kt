package com.uvn.ticker.ui.tickerview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import com.uvn.ticker.data.model.TickerTextState
import com.uvn.ticker.data.TickerTextStateController

class TickerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    BaseTickerView(context, attrs) {

    private var messages = mutableListOf<TickerTextStateController>()

    fun setSpeed(step: Float) {
        messages.clear()
        params.textSpeed = step
    }

    fun setTextRatio(ratio: Float) {
        messages.clear()
        calculateWithRatio(ratio)
        params.textRatio = ratio
    }

    fun setGap(newGap: String) {
        messages.clear()
        params.gap = newGap
        textWidth = paint.measureText(params.text + params.gap)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (messages.isEmpty()) messages.add(initTicker())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val fm = paint.fontMetrics
        val textHeight = fm.descent - fm.ascent

        paint.color = params.textColor
        canvas.drawColor(params.backgroundColor)

        messages.forEach { text ->
            drawText(canvas, text.position.toInt(), paint, textHeight)
            text.move()
        }

        if (messages.all { it.getTextState() == TickerTextState.VISIBLE }) {
            messages.add(initTicker())
        }

        if (messages.first().getTextState() == TickerTextState.LEFT_OF_SCREEN) {
            messages.removeAt(0)
        }

        postInvalidateDelayed(5)
    }
}
