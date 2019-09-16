package com.uvn.ticker.ui.tickerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.uvn.ticker.data.model.TickerParam
import com.uvn.ticker.data.TickerTextStateController

open class BaseTickerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {
    lateinit var params: TickerParam
    val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)

    private var halfScreenHeight = 0f
    private var screenWidth = 0f
    var screenHeight = 0f
    var textWidth = 0f

    fun initParams(params: TickerParam) = params.let {
        this.params = it
        with(paint) {
            color = it.textColor
            textWidth = measureText(it.text + it.gap.length)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()

        calculateWithRatio(params.textRatio)
        halfScreenHeight = h.toFloat() / 2
    }

    fun calculateWithRatio(ratio: Float) {
        val textSizeWithHeightRatio = (screenHeight) * ratio
        paint.textSize = textSizeWithHeightRatio
        textWidth = paint.measureText(params.text + params.gap)
    }

    fun drawText(canvas: Canvas, step: Int, paint: TextPaint, textHeight: Float) =
        canvas.drawText(
            params.text + params.gap,
            screenWidth - step,
            halfScreenHeight + textHeight / 4,
            paint
        )

    fun initTicker() =
        TickerTextStateController(
            textWidth,
            screenWidth,
            step = params.textSpeed
        )
}