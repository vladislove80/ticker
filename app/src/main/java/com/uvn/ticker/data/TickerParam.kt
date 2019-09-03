package com.uvn.ticker.data

import android.graphics.Color
import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.android.parcel.Parcelize

@Parcelize
class TickerParam(
    val text: String,
    var textRatio: Float = 1f / 20,
    var fontRes: String = "sans-serif-medium",
    var textSpeed: Float = 1f,
    @ColorInt var backgroundColor: Int = Color.WHITE,
    @ColorInt var textColor: Int = Color.BLACK,
    var fromRightToLeft: Boolean = true
) : Parcelable