package com.uvn.ticker

import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.uvn.ticker.tickerview.TickerScreenParams
import com.uvn.ticker.tickerview.TickerViewActivity
import kotlinx.android.synthetic.main.activity_customize.*

const val TAG_TICKER_PARAMS = "ticker_params"

class CustomizerActivity : AppCompatActivity() {

    private val seekerPositionRanges = listOf(
        0..5, 6..10, 11..15, 16..20, 21..25, 26..30, 31..35, 36..40, 41..45, 46..50, 51..55, 56..60,
        61..65, 66..70, 71..75, 76..80, 81..85, 86..90, 91..95, 96..100
    )

    private val speedRanges = listOf(
        1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 11f, 12f, 13f, 14f, 15f, 16f,
        17f, 18f, 19f, 20f
    )
    private val ratioRanges = listOf(
        1f / 20, 2f / 20, 3f / 20, 4f / 20, 5f / 20, 6f / 20, 7f / 20, 8f / 20, 9f / 20, 10f / 20,
        11f / 20, 12f / 20, 13f / 20, 14f / 20, 15f / 20, 16f / 20, 17f / 20, 18f / 20, 19f / 20, 1f
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customize)

        intent.extras?.getString(TAG_TICKER_MESSAGE)?.let { message ->
            tvPreview.initParams(TickerScreenParams(message))
        }

        setListeners()
    }

    private fun setListeners() {
        sbSpeed.setOnSeekBarChangeListener(onProgress { seekerPosition ->
            setTickerViewValue(seekerPosition) { index ->
                tvPreview.setSpeed(speedRanges[index])
            }
        })

        sbTextSize.setOnSeekBarChangeListener(onProgress { seekerPosition ->
            setTickerViewValue(seekerPosition) { index ->
                tvPreview.setTextRatio(ratioRanges[index])
            }
        })

        btnGotIt.setOnClickListener {
            startActivity(Intent(this, TickerViewActivity::class.java).apply {
                putExtra(TAG_TICKER_PARAMS, tvPreview.params)
            })
        }
    }

    fun setTickerViewValue(seekerPosition: Int, set: (Int) -> Unit) {
        seekerPositionRanges.forEachIndexed { index, seekerRanges ->
            if (seekerPosition in seekerRanges) set.invoke(index)
        }
    }

    private fun onProgress(find: (Int) -> Unit): SeekBar.OnSeekBarChangeListener {
        return object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                find.invoke(p1)
            }
        }
    }
}
