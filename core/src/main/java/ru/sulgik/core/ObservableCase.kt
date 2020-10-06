package ru.sulgik.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlin.coroutines.CoroutineContext

class ObservableCase<T, P>(
    internal val scope: CoroutineScope,
    internal val context: CoroutineContext,
    private val case: UseCase<T, P>
    ) {

    private val channel = Channel<Result<T>>()
    val flow = channel.receiveAsFlow()

    fun execute(parameter: P) {
        case.execute(this, parameter)
    }

    private fun emit(value: Result<T>) {
        channel.offer(value)
    }

    internal fun emitCancellation() {
        emit(Result.Canceled())
    }

    internal fun emitFailing(cause: Throwable) {
        emit(Result.Failure(cause))
    }

    internal fun emitResult(result: T) {
        emit(Result.Successful(result))
    }

    fun cancel() {
        case.cancel()
    }


}