package com.uvn.ticker.data

import android.os.Handler
import android.os.Looper
import com.uvn.ticker.TickerApp
import com.uvn.ticker.data.database.TickerParamEntity
import java.util.concurrent.ExecutorService

object Repository {

    private val appDatabase = TickerApp.instance.database
    private val tickerParamsDao = appDatabase.tickerParamDao()
    private val executorService: ExecutorService

    private val tParams = mutableListOf<TickerParam>()

    init {
        executorService = TaskExecutor().getThreadPoolExecutor()

        Handler(Looper.getMainLooper()).post {
            executorService.execute {
                tParams.addAll(appDatabase.tickerParamDao().getAllTickerParam().map {
                    convertEntityToParam(it)
                })
            }
        }
    }

    fun getTickerParams() = tParams

    fun saveTickerParam(tp: TickerParam) {
        tParams.find { it.text == tp.text }?.let {
            it.textRatio = tp.textRatio
            it.fontRes = tp.fontRes
            it.textSpeed = tp.textSpeed
            it.backgroundColor = tp.backgroundColor
            it.textColor = tp.textColor
            it.fromRightToLeft = tp.fromRightToLeft
            it.gap = tp.gap
        }
        executorService.execute {
            tickerParamsDao.insert(convertParamToEntity(tp))
        }
    }

    fun deleteTickerParam(tp: TickerParam) {
        tParams.remove(tp)
        executorService.execute {
            tickerParamsDao.delete(convertParamToEntity(tp))
        }
    }

    fun clearHistory() {
        tParams.clear()
        executorService.execute {
            tickerParamsDao.deleteAll()
        }
    }

    private fun convertEntityToParam(tpe: TickerParamEntity) = TickerParam(
        tpe.text,
        tpe.textRatio,
        tpe.fontRes,
        tpe.textSpeed,
        tpe.backgroundColor,
        tpe.textColor,
        tpe.fromRightToLeft,
        tpe.gap
    )

    private fun convertParamToEntity(tp: TickerParam): TickerParamEntity = TickerParamEntity(
        tp.text,
        tp.textRatio,
        tp.fontRes,
        tp.textSpeed,
        tp.backgroundColor,
        tp.textColor,
        tp.fromRightToLeft,
        tp.gap
    )
}