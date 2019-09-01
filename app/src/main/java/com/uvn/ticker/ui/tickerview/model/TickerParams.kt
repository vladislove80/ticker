package com.uvn.ticker.ui.tickerview.model

import android.graphics.Color
import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.android.parcel.Parcelize

@Parcelize
class TickerParams(
    var text: String = "",
    var textRatio: Float = 1f / 20,
    val fontRes: String = "sans-serif-medium",
    var textSpeed: Float = 1f,
    @ColorInt var backgroundColor: Int = Color.WHITE,
    @ColorInt var textColor: Int = Color.BLACK,
    val fromRightToLeft: Boolean = true
) : Parcelable