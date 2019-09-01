package com.uvn.ticker.ui.tickerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
}
