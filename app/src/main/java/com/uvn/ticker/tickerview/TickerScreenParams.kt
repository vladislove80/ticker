package com.uvn.ticker.tickerview

import android.graphics.Color
import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.android.parcel.Parcelize

@Parcelize
class TickerScreenParams(
    val text: String,
    val textSize: Float = 18f,
    val fontRes: String = "sans-serif-medium",
    val textSpeed: Float = 40f,
    @ColorInt val backgroundColor: Int = Color.WHITE,
    @ColorInt val textColor: Int = Color.BLACK,
    val fromRightToLeft: Boolean = true
) : Parcelable