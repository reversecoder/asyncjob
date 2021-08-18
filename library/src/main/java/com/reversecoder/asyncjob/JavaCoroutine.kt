package com.reversecoder.asyncjob

import com.reversecoder.asyncjob.wrapper.FutureJobWrapper
import com.reversecoder.asyncjob.wrapper.JobWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future

/**
 * This class is responsible for creating coroutine jobs.
 *
 * Call a method to create a JobWrapper.
 *
 */
class JavaCoroutine() {

    /**
     * Create an asynchronous job that runs one time.
     *
     * @param runnable [Runnable] to run
     * @return the created [JobWrapper]
     */
    fun async(runnable: Runnable): JobWrapper {
        val wrapper = JobWrapper()
        val job = GlobalScope.launch {
            async {
                runnable.run()
            }
        }

        wrapper.setJob(job)
        return wrapper
    }

    /**
     * Create an asynchronous job that runs one time with an initial delay.
     *
     * @param runnable [Runnable] to run
     * @param delay [Long] initial delay
     * @return the created [JobWrapper]
     */
    fun asyncDelayed(runnable: Runnable, delay: Long): JobWrapper {
        val wrapper = JobWrapper()
        val job = GlobalScope.launch {
            async {
                kotlinx.coroutines.delay(delay)
                runnable.run()
            }
        }

        wrapper.setJob(job)
        return wrapper
    }

    /**
     * Create an asynchronous job that runs until it is cancelled, with an initial and repeating delay.
     *
     * @param runnable [Runnable] to run
     * @param initialDelay [Long] initial delay
     * @param repeatingDelay [Long] repeating delay
     * @return the created [JobWrapper]
     */
    fun asyncRepeating(runnable: Runnable, initialDelay: Long, repeatingDelay: Long): JobWrapper {
        val wrapper = JobWrapper()
        val job = GlobalScope.launch {
            async {
                kotlinx.coroutines.delay(initialDelay)
                while (wrapper.isRunning()) {
                    runnable.run()
                    kotlinx.coroutines.delay(repeatingDelay)
                }
            }
        }

        wrapper.setJob(job)
        return wrapper
    }

    /**
     * Create an asynchronous job that returns a [FutureJobWrapper] with an underlying [Future] that can be used after completion.
     *
     * @param callable [Callable] to call
     * @return the created [FutureJobWrapper]
     */
    fun await(callable: Callable<*>): FutureJobWrapper {
        val wrapper = FutureJobWrapper()
        val completedFuture = CompletableFuture<Any>()
        wrapper.setFuture(completedFuture)
        val job = GlobalScope.launch {
            async {
                completedFuture.complete(callable.call())
            }
        }

        wrapper.setJob(job)
        return wrapper
    }

    /**
     * Delays a coroutine by a specific duration, this is blocking.
     *
     * @param delay time duration
     */
    fun delay(delay: Long) {
        runBlocking {
            kotlinx.coroutines.delay(delay)
        }
    }

    /**
     * Runs a [Runnable] that blocks the current thread until completion
     *
     * @param runnable The runnable to run
     */
    fun runBlocking(runnable: Runnable) {
        runBlocking {
            runnable.run()
        }
    }
}