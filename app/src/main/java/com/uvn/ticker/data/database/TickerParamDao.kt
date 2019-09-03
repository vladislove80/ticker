package com.uvn.ticker.data.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface TickerParamDao {

    @Insert(onConflict = REPLACE)
    fun insert(param: TickerParamEntity)

    @Query("SELECT * FROM $PARAMS_TABLE")
    fun getAllTickerParam(): MutableList<TickerParamEntity>

    @Delete
    fun delete(tp: TickerParamEntity)

    @Query("DELETE FROM $PARAMS_TABLE")
    fun deleteAll()

    @Update(onConflict = REPLACE)
    fun update(tp: TickerParamEntity)
}