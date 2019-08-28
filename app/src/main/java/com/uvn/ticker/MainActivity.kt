package com.uvn.ticker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uvn.ticker.tickerview.TickerScreenParams
import com.uvn.ticker.tickerview.TickerViewActivity

const val TAG_TICKER_PARAMS = "ticker_params"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, TickerViewActivity::class.java).apply {
            putExtra(
                TAG_TICKER_PARAMS, TickerScreenParams(
                    "Сдам квартиру у моря, цена договорная) ",
                    textSize = 500f,
                    textSpeed = 20f
                )
            )
        })
    }
}
