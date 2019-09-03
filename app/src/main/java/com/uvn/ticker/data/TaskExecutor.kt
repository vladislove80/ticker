package com.uvn.ticker.data

import java.util.concurrent.*

class TaskExecutor : Executor {
    private val PROCESSORS = Runtime.getRuntime().availableProcessors()
    private val OOLSIZE = 4
    private val INITIAL_POOL_SIZE = if (PROCESSORS > 1) PROCESSORS else 2
    private val MAX_POOL_SIZE = INITIAL_POOL_SIZE * OOLSIZE
    private val KEEP_ALIVE_TIME = 15
    private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS

    private val threadPoolExecutor: ThreadPoolExecutor

    init {
        val workQueue = LinkedBlockingQueue<Runnable>()
        val threadFactory = JobThreadFactory()
        this.threadPoolExecutor = ThreadPoolExecutor(
            INITIAL_POOL_SIZE, MAX_POOL_SIZE,
            KEEP_ALIVE_TIME.toLong(), KEEP_ALIVE_TIME_UNIT, workQueue, threadFactory
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