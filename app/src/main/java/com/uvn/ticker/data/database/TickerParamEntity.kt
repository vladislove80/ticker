package com.uvn.ticker.data.database

import androidx.annotation.ColorInt
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = PARAMS_TABLE, primaryKeys = ["text"])
class TickerParamEntity(
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "textRatio") var textRatio: Float,
    @ColumnInfo(name = "fontRes") val fontRes: String,
    @ColumnInfo(name = "textSpeed") var textSpeed: Float,
    @ColumnInfo(name = "backgroundColor") @ColorInt val backgroundColor: Int,
    @ColumnInfo(name = "textColor") @ColorInt val textColor: Int,
    @ColumnInfo(name = "fromRightToLeft") val fromRightToLeft: Boolean
)