package com.uvn.ticker.ui.editexteactivity.history

import com.uvn.ticker.data.model.TickerParam

interface HistoryClickListener {
    fun onClick(tp: TickerParam)
}