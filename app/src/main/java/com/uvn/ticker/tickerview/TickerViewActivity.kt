package com.uvn.ticker.tickerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uvn.ticker.R
import com.uvn.ticker.TAG_TICKER_PARAMS
import kotlinx.android.synthetic.main.activity_tricker_view.*

class TickerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tricker_view)

        intent.extras?.getParcelable<TickerScreenParams>(TAG_TICKER_PARAMS)?.let { params ->
            tickerView.setParams(params)
        }
    }
}
