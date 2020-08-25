package ru.sulgik.common.arch

import kotlinx.coroutines.*

/**
 * Abstract class which need to implement inside run block your functional
 * @author Vova Nenashkin
 * @since 1.0
 * @see ObservableCase
 */
abstract class UseCase <T, P>{

    /**
     * Async block will be invoke when it will be execute
     * @param cont is collecting param and callback
     */
    protected abstract suspend fun run(cont: UseCaseContinuation<T, P>)

    /**
     * Job which is launched
     * @see cancel
     */
    private var job : Job = Job()

    /**
     * Cancel started job
     * @see launch
     */
    internal fun cancel(){
        job.cancel()
    }

    /**
     * Entry point into launching job
     * @param continuation is collecting param and callback
     * @see executeWithCoroutine
     */
    internal fun execute(continuation: ObservableUseCaseContinuation<T,P>){
        executeWithCoroutine(continuation)
    }

    /**
     * Configure [UseCase] to start job via coroutine
     * @param continuation is collecting param and callback
     * @see getCompetitionHandler
     * @see getExceptionHandler
     */
    private fun executeWithCoroutine(continuation : ObservableUseCaseContinuation<T,P>){
        job = continuation.launch(getExceptionHandler(continuation)) {
            run(continuation)
        }.apply{ invokeOnCompletion(getCompetitionHandler(continuation)) }
    }

    /**
     * Job competition handlers builder
     * @param continuation is collecting param and callback
     * @see getExceptionHandler
     * @see executeWithCoroutine
     */
    private fun getCompetitionHandler(continuation: ObservableUseCaseContinuation<T, P>): (Throwable?) -> Unit {
        return { cause : Throwable? ->
            if (cause is CancellationException) {
                continuation.emitCancel()
            }
            continuation.emitExecutionCompletion()
        }
    }

    /**
     * Job excepted situations handlers builder
     * @see getCompetitionHandler
     * @see executeWithCoroutine
     */
    private fun getExceptionHandler(continuation: ObservableUseCaseContinuation<T, P>): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            continuation.emitError(throwable)
        }
    }

}

/**
 * Impl class to quick creating [UseCase] via lambda
 * @param runnable will be invoked on [UseCase.run]
 * @see useCase
 * @since 1.0
 */
private class UseCaseImpl<T, P>(private val runnable : suspend (cont : UseCaseContinuation<T,P>) -> Unit) : UseCase<T, P>() {
    override suspend fun run(continuation: UseCaseContinuation<T,P>) {
        runnable.invoke(continuation)
    }

}


/**
 * Simple lambda function to quick creating [UseCase]
 * @param runnable is [UseCase.run]
 * @since 1.0
 */
fun <T, P> useCase (runnable : suspend (cont : UseCaseContinuation<T,P>) -> Unit) : UseCase<T, P> {
    return UseCaseImpl(runnable)
}