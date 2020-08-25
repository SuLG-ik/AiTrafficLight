package ru.sulgik.common.arch

import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Extension function to simple collect flow stream
 */
inline fun <T> Flow<T>.collect(
    scope : CoroutineScope,
    context : CoroutineContext = EmptyCoroutineContext,
    start : CoroutineStart = CoroutineStart.DEFAULT,
    crossinline action : suspend (T) -> Unit): Job {
    return scope.launch(context, start) {
        this@collect.collect(action)
    }
}