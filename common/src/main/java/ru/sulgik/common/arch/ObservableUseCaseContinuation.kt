package ru.sulgik.common.arch

import kotlinx.coroutines.*

/**
 * Mutable implementation of [UseCaseContinuation]
 */
class ObservableUseCaseContinuation <T, P> (private val case : ObservableCase<T, P>, override val param: P) : UseCaseContinuation<T, P> {

    private var onCancel = {}

    override fun invokeOnCancel(action: () -> Unit) {
        onCancel = action
    }

    override fun emitResult(result: T) {
        case.emitResult(result)
    }

    override fun emitLoading() {
        case.emitLoading()
    }

    override fun emitError(t: Throwable){
        case.emitError(t)
    }

    internal fun emitCancel(){
        case.emitCancel()
        onCancel()
    }

    internal fun emitExecutionCompletion() {
        case.emitExecutionCompletion()
    }

    internal fun launch(
        handler : CoroutineExceptionHandler,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return case.launch(handler, block)
    }

}