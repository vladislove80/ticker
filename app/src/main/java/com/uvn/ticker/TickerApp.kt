package com.uvn.ticker

import android.app.Application
import com.uvn.ticker.data.database.AppDatabase

class TickerApp : Application() {

    lateinit var database: AppDatabase

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getDatabase(this)
    }

    companion object {
        lateinit var instance: TickerApp
            private set
    }
}
