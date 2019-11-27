package com.runeanim.mytoyproject.base

import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

interface BasePresenter {

    private val job: CompletableJob
        get() = SupervisorJob()
    val coroutineScope: CoroutineScope
        get() = CoroutineScope(Dispatchers.Main + job)

    fun start()

    fun finish(){
        job.cancel()
    }
}
