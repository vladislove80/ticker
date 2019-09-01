package com.uvn.ticker.ui.tickerview

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.uvn.ticker.R
import com.uvn.ticker.ui.TAG_TICKER_PARAMS
import com.uvn.ticker.ui.tickerview.model.TickerParams
import kotlinx.android.synthetic.main.activity_tricker_view.*

class TickerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tricker_view)

        intent.extras?.getParcelable<TickerParams>(TAG_TICKER_PARAMS)?.let { params ->
            tickerView.initParams(params)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
        else showSystemUI()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
}
