package com.uvn.ticker.data

import android.graphics.Color
import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.android.parcel.Parcelize

@Parcelize
class TickerParams(
    val text: String,
    var textRatio: Float = 1f / 20,
    val fontRes: String = "sans-serif-medium",
    var textSpeed: Float = 1f,
    @ColorInt val backgroundColor: Int = Color.WHITE,
    @ColorInt val textColor: Int = Color.BLACK,
    val fromRightToLeft: Boolean = true
) : Parcelable