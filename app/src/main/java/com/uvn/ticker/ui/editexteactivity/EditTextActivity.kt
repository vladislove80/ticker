package com.uvn.ticker.ui.editexteactivity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.uvn.ticker.PreferenceHelper
import com.uvn.ticker.R
import com.uvn.ticker.ui.CustomizerActivity
import com.uvn.ticker.ui.editexteactivity.history.HistoryClickListener
import com.uvn.ticker.ui.editexteactivity.history.HistoryHistoryAdapter
import com.uvn.ticker.ui.editexteactivity.history.HistoryTouchCallback
import com.uvn.ticker.ui.editexteactivity.history.HistoryTouchHelper
import com.uvn.ticker.ui.tickerview.model.TickerParams
import kotlinx.android.synthetic.main.activity_main.*


const val TAG_TICKER_MESSAGE = "message"

class EditTextActivity : AppCompatActivity(),
    HistoryClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= 21) etMessage.showSoftInputOnFocus = true

        tvLabelTicker.initParams(
            TickerParams(
                "Edit text customize it and go ticker.",
                textSpeed = 6f,
                textRatio = 4f / 20
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
                val messages = (rwHistory.adapter as HistoryHistoryAdapter).messages
                if (!messages.contains(message)) {
                    messages.add(message)
                    PreferenceHelper.saveMessages(this, messages)
                }
                startCustomizerActivity(message)
            }
        }
    }

    private fun setHistoryList() {
        with(rwHistory) {
            val messages = PreferenceHelper.loadMessages(this@EditTextActivity)
            adapter = HistoryHistoryAdapter(this@EditTextActivity, messages) {
                deleteMessageAndSave(messages)
            }
            layoutManager = LinearLayoutManager(this@EditTextActivity)
            setHasFixedSize(true)
            val tCallback = HistoryTouchCallback(adapter as HistoryTouchHelper)
            val touchHelper = ItemTouchHelper(tCallback)
            touchHelper.attachToRecyclerView(this)
        }
    }

    private fun checkHistoryLabel(messages: MutableList<String>) {
        tvHistory.visibility = if (messages.isEmpty()) GONE else VISIBLE
    }

    private fun deleteMessageAndSave(messages: MutableList<String>) {
        checkHistoryLabel(messages)
        PreferenceHelper.saveMessages(this@EditTextActivity, messages)
    }

    private fun startCustomizerActivity(message: String) {
        startActivity(Intent(this, CustomizerActivity::class.java).apply {
            putExtra(TAG_TICKER_MESSAGE, "$message ")
        })
    }

    override fun onClick(message: String) {
        if (message.isNotEmpty()) etMessage.setText(message, TextView.BufferType.EDITABLE)
        startCustomizerActivity(message)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edittextactivity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clearAll) {
            val adapter = rwHistory.adapter as HistoryHistoryAdapter
            adapter.clearAll()
            checkHistoryLabel(adapter.messages)
            PreferenceHelper.saveMessages(this@EditTextActivity, adapter.messages)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        checkHistoryLabel((rwHistory.adapter as HistoryHistoryAdapter).messages)
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
}
