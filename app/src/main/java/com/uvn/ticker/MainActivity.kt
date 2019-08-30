package com.uvn.ticker

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

const val TAG_TICKER_MESSAGE = "message"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= 21) etMessage.showSoftInputOnFocus = true

        btnNext.setOnClickListener {
            if (etMessage.text.isNotEmpty()) {
                startActivity(Intent(this, CustomizerActivity::class.java).apply {
                    putExtra(TAG_TICKER_MESSAGE, etMessage.text.toString() + " ")
                })
            }
        }
    }

/*    override fun onResume() {
        super.onResume()

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(etMessage, InputMethodManager.SHOW_IMPLICIT)
    }*/
}
