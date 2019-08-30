package com.uvn.ticker

import android.content.Context
import com.google.gson.Gson

const val PREFERENCES_NAME = "prefs"
const val PREFERENCES_KEY = "key"

object PreferenceHelper {

    fun saveMessages(context: Context, list: MutableList<String>) {
        val json = Gson().toJson(list)
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit().putString(PREFERENCES_NAME, json).apply()

        val set = HashSet<String>()
        set.addAll(list)
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit().putStringSet(PREFERENCES_KEY, set).apply()
    }

    fun loadMessages(context: Context): MutableList<String> {
        val set: MutableSet<String>? =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
                .getStringSet(PREFERENCES_KEY, null)
        val mutableListOf = mutableListOf<String>()
        set?.let { mutableListOf.addAll(it) }
        mutableListOf.sort()
        return mutableListOf
    }
}