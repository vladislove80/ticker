package com.uvn.ticker.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uvn.ticker.R
import com.uvn.ticker.ui.editexteactivity.EditTextActivity
import com.uvn.ticker.ui.tickerview.model.TickerParams
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        tvLabelTicker.initParams(
            TickerParams(
                "Ticker           ",
                textSpeed = 15f,
                textRatio = 4f / 20
            )
        )

        tvLabelTicker.postDelayed({
            finish()
            startActivity(Intent(this, EditTextActivity::class.java))
        }, 2000)
    }
}
