package com.uvn.ticker.editexteactivity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.uvn.ticker.CustomizerActivity
import com.uvn.ticker.PreferenceHelper
import com.uvn.ticker.R
import com.uvn.ticker.editexteactivity.history.HistoryClickListener
import com.uvn.ticker.editexteactivity.history.HistoryHistoryAdapter
import com.uvn.ticker.editexteactivity.history.HistoryTouchCallback
import com.uvn.ticker.editexteactivity.history.HistoryTouchHelper
import kotlinx.android.synthetic.main.activity_main.*


const val TAG_TICKER_MESSAGE = "message"

class EditTextActivity : AppCompatActivity(),
    HistoryClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= 21) etMessage.showSoftInputOnFocus = true
        setButtonListener()
        setHistoryList()
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
}
