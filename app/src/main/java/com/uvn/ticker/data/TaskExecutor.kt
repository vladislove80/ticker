package com.uvn.ticker.data

import java.util.concurrent.*

class TaskExecutor : Executor {
    private val processors = Runtime.getRuntime().availableProcessors()
    private val poolSize = 4
    private val initialPoolSize = if (processors > 1) processors else 2
    private val maxPoolSize = initialPoolSize * poolSize
    private val keepAliveTime = 15
    private val keepAliveTimeUnit = TimeUnit.SECONDS

    private val threadPoolExecutor: ThreadPoolExecutor

    init {
        val workQueue = LinkedBlockingQueue<Runnable>()
        val threadFactory = JobThreadFactory()
        this.threadPoolExecutor = ThreadPoolExecutor(
            initialPoolSize, maxPoolSize,
            keepAliveTime.toLong(), keepAliveTimeUnit, workQueue, threadFactory
        )
    }

    fun getThreadPoolExecutor(): ThreadPoolExecutor {
        return threadPoolExecutor
    }

    override fun execute(command: Runnable) {
        this.threadPoolExecutor.execute(command)
    }
}

private class JobThreadFactory : ThreadFactory {
    private var counter = 0

    override fun newThread(runnable: Runnable): Thread {
        return Thread(runnable, THREAD_NAME + counter++)
    }

    companion object {
        private const val THREAD_NAME = "ticker-thread-"
    }
}