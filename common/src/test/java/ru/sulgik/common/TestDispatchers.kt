package ru.sulgik.common

import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
object TestDispatchers {

    val Worker = newSingleThreadContext("Worker thread")
    val Main = TestCoroutineDispatcher()
    val PostThread = newSingleThreadContext("Post thread")

    fun resetWorker(){
        Worker.cancelChildren()
        PostThread.cancelChildren()
    }

    fun setupMain(){
        Dispatchers.setMain(Main)
    }

    fun cleanupMain(){
        Main.cleanupTestCoroutines()
        Dispatchers.resetMain()
    }

}