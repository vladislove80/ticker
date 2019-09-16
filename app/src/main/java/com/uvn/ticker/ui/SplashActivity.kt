package com.uvn.ticker.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uvn.ticker.R
import com.uvn.ticker.data.model.TickerParam
import com.uvn.ticker.ui.editexteactivity.EditTextActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvLabelTicker.initSingleTickerParams(
            TickerParam(
                "Ticker",
                textSpeed = 20f,
                textRatio = 4f / 20
            )
        ) {
            startActivity(Intent(this@SplashActivity, EditTextActivity::class.java))
            finish()
        }
    }
}
