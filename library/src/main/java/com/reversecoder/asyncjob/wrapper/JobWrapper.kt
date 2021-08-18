package com.reversecoder.asyncjob.wrapper

import kotlinx.coroutines.Job
import java.util.concurrent.atomic.AtomicBoolean

open class JobWrapper() {

    private val running = AtomicBoolean(true)
    private var job: Job? = null

    fun getJob(): Job? {
        return this.job
    }

    fun setJob(job: Job?) {
        this.job = job
    }

    fun isRunning(): Boolean {
        return this.running.get() && this.job!!.isActive
    }

    fun stop() {
        this.running.set(false)
        this.job!!.cancel()
    }
}