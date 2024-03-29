package com.uvn.ticker.ui

import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.uvn.ticker.R

import com.uvn.ticker.ui.editexteactivity.TAG_TICKER_MESSAGE
import com.uvn.ticker.ui.tickerview.TickerViewActivity
import com.uvn.ticker.ui.tickerview.model.TickerParams
import kotlinx.android.synthetic.main.activity_customize.*

const val TAG_TICKER_PARAMS = "ticker_params"

class CustomizerActivity : AppCompatActivity(R.layout.activity_customize) {

    private val seekerPositionRanges = listOf(
        0..5, 6..10, 11..15, 16..20, 21..25, 26..30, 31..35, 36..40, 41..45, 46..50, 51..55, 56..60,
        61..65, 66..70, 71..75, 76..80, 81..85, 86..90, 91..95, 96..100
    )

    private val speedRanges = listOf(
        1.5f, 3f, 4.5f, 6f, 7.5f, 9f, 10.5f, 12f, 13.5f, 15f, 16.5f, 18f, 19.5f, 21f, 22.5f, 22.5f,
        22.5f, 22.5f, 22.5f, 22.5f
    )
    private val ratioRanges = listOf(
        1f / 20, 2f / 20, 3f / 20, 4f / 20, 5f / 20, 6f / 20, 7f / 20, 8f / 20, 9f / 20, 10f / 20,
        11f / 20, 12f / 20, 13f / 20, 14f / 20, 15f / 20, 16f / 20, 17f / 20, 18f / 20, 19f / 20, 1f
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.getString(TAG_TICKER_MESSAGE)?.let { message ->
            tvPreview.initParams(TickerParams(message))
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

    private fun setTickerViewValue(seekerPosition: Int, set: (Int) -> Unit) {
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
