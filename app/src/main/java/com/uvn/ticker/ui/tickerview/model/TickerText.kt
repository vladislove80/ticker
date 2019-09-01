package com.uvn.ticker.ui.tickerview.model

class TickerText(
    private val textWidth: Float,
    private val screenWidth: Float,
    private val fromRightToLeft: Boolean = true,
    var step: Float,
    private var textState: TickerTextState = TickerTextState.RIGHT_OF_SCREEN
) {
    var position = 0f
    private val gap: Float = step + 1

    fun move() {
        position += step
        switchTickerState(position, fromRightToLeft)
    }

    private fun switchTickerState(position: Float, fromRightToLeft: Boolean) {
        if (fromRightToLeft) switchTextStateFromRight(position)
        else switchTextStateFromLeft(position)
    }

    private fun switchTextStateFromLeft(position: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun switchTextStateFromRight(position: Float) {
        if (position > textWidth && position < textWidth + gap) {
            textState = TickerTextState.END_TO_SCREEN_END
        }

        if (position > textWidth + gap) {
            textState = TickerTextState.VISIBLE
        }

        if (position > screenWidth && position < screenWidth + gap) {
            textState = TickerTextState.START_TO_SCREEN_START
        }

        if (position > screenWidth + textWidth && position < screenWidth + textWidth + gap) {
            textState = TickerTextState.LEFT_OF_SCREEN
        }
    }

    fun getTextState() = textState
}