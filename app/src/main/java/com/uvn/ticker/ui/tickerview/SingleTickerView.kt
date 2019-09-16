package com.uvn.ticker.ui.tickerview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import com.uvn.ticker.data.TickerParam
import com.uvn.ticker.data.TickerTextState
import com.uvn.ticker.data.TickerTextStateController

class SingleTickerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    BaseTickerView(context, attrs) {

    private lateinit var singleTickerController: TickerTextStateController
    private lateinit var completed: () -> Unit

    fun initSingleTickerParams(params: TickerParam, completed: () -> Unit) {
        initParams(params)
        this.completed = completed
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (!::singleTickerController.isInitialized) singleTickerController = initTicker()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!::singleTickerController.isInitialized) return

        val fm = paint.fontMetrics
        val textHeight = fm.descent - fm.ascent

        paint.color = params.textColor
        canvas.drawColor(params.backgroundColor)

        drawText(canvas, singleTickerController.position.toInt(), paint, textHeight)
        singleTickerController.move()

        if (singleTickerController.getTextState() == TickerTextState.LEFT_OF_SCREEN) {
            if (::completed.isInitialized) completed.invoke()
        } else postInvalidateDelayed(5)
    }
}