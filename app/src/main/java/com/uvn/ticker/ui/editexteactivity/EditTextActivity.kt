package com.uvn.ticker.ui.editexteactivity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.uvn.ticker.R
import com.uvn.ticker.data.Repository
import com.uvn.ticker.data.model.TickerParam
import com.uvn.ticker.ui.CustomizerActivity
import com.uvn.ticker.ui.TAG_TICKER_PARAMS
import com.uvn.ticker.ui.editexteactivity.history.HistoryClickListener
import com.uvn.ticker.ui.editexteactivity.history.HistoryHistoryAdapter
import com.uvn.ticker.ui.editexteactivity.history.HistoryTouchCallback
import com.uvn.ticker.ui.editexteactivity.history.HistoryTouchHelper
import kotlinx.android.synthetic.main.edittext_main.*

class EditTextActivity : AppCompatActivity(R.layout.edittext_main),
    HistoryClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= 21) etMessage.showSoftInputOnFocus = true
        tvLabelTicker.initParams(
            TickerParam(
                "Edit text, customize it and go Ticker! *",
                textSpeed = 6f,
                textRatio = 7f / 20
            )
        )
        setButtonListener()
        setHistoryList()
        etMessage.setupClearButtonWithAction()
    }

    private fun setButtonListener() {
        btnNext.setOnClickListener {
            val message = etMessage.text.toString()
            if (message.isNotEmpty()) {
                val tp = (rwHistory.adapter as HistoryHistoryAdapter).tp
                val isNew = !tp.any {
                    it.text == message
                }
                if (isNew) {
                    val tickerParam = TickerParam(message)
                    tp.add(tickerParam)
                    Repository.saveTickerParam(tickerParam)
                    startCustomizerActivity(tickerParam)
                }
            }
        }
    }

    private fun setHistoryList() {
        with(rwHistory) {
            adapter = HistoryHistoryAdapter(this@EditTextActivity, Repository.getTickerParams()) {
                deleteAndSave(it)
            }
            layoutManager = LinearLayoutManager(this@EditTextActivity)
            setHasFixedSize(true)
            ItemTouchHelper(HistoryTouchCallback(adapter as HistoryTouchHelper))
                .apply {
                    attachToRecyclerView(this@with)
                }
        }
    }

    private fun checkHistoryLabel(messages: MutableList<TickerParam>) {
        tvHistory.visibility = if (messages.isEmpty()) GONE else VISIBLE
    }

    private fun deleteAndSave(tp: TickerParam) {
        checkHistoryLabel((rwHistory.adapter as HistoryHistoryAdapter).tp)
        Repository.deleteTickerParam(tp)
    }

    private fun startCustomizerActivity(tp: TickerParam) {
        startActivity(Intent(this, CustomizerActivity::class.java).apply {
            putExtra(TAG_TICKER_PARAMS, tp)
        })
    }

    override fun onClick(tp: TickerParam) {
        etMessage.setText(tp.text, TextView.BufferType.EDITABLE)
        startCustomizerActivity(tp)
        (rwHistory.adapter as HistoryHistoryAdapter).tp.logger("EditTextActivity")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edittextactivity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clearAll) {
            val adapter = rwHistory.adapter as HistoryHistoryAdapter
            adapter.clearAll()
            checkHistoryLabel(adapter.tp)
            Repository.clearHistory()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        checkHistoryLabel(Repository.getTickerParams())
    }

    private fun EditText.setupClearButtonWithAction() {

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                val clearIcon = if (editable?.isNotEmpty() == true) R.drawable.ic_clear_black else 0
                setCompoundDrawablesWithIntrinsicBounds(0, 0, clearIcon, 0)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        })

        setOnTouchListener(View.OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (this.right - this.compoundPaddingRight)) {
                    this.setText("")
                    return@OnTouchListener true
                }
            }
            return@OnTouchListener false
        })
    }

    private fun List<TickerParam>.logger(tag: String) {
        Log.d(tag, "List size: ${this.size}, \n ")
        this.forEach {
            Log.d(tag, "Items text: ${it.text}, ")
        }
    }
}
