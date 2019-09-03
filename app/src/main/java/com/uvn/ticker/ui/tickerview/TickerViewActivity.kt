package com.uvn.ticker.ui.tickerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uvn.ticker.R
import com.uvn.ticker.ui.TAG_TICKER_PARAMS
import com.uvn.ticker.data.TickerParam
import kotlinx.android.synthetic.main.activity_tricker_view.*

class TickerViewActivity : AppCompatActivity(R.layout.activity_tricker_view) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.getParcelable<TickerParam>(TAG_TICKER_PARAMS)?.let { params ->
            tickerView.initParams(params)
        }
    }
}
