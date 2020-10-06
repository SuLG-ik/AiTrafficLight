package ru.sulgik.core

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class UseCase<T, P> {

    abstract suspend fun run(parameter: P)

    open fun onComplete() {}


    private var parent: ObservableCase<T, P>? = null
    private var isExecuted: Boolean = false

    private var job: Job? = null

    internal fun execute(case: ObservableCase<T, P>, parameter: P): Boolean {
        if (isExecuted)
            return false
        parent = case
        isExecuted = true
        executeWithCoroutine(case, parameter)
        return true
    }


    private fun executeWithCoroutine(case: ObservableCase<T, P>, parameter: P) {
        job = case.scope.launch(case.context) {
            try {
                run(parameter)
            } catch (e: CancellationException) {
                emitCancellation()
            } catch (t: Throwable) {
                emitFailing(t)
            } finally {
                emitCompletion()
            }
        }
    }

    protected fun emitCancellation() {
        parent?.emitCancellation()
    }

    protected fun emitFailing(cause: Throwable) {
        parent?.emitFailing(cause)
    }

    protected fun emitResult(result: T) {
        parent?.emitResult(result)
    }

    protected fun emitCompletion() {
        isExecuted = false
        onComplete()
    }

    internal fun cancel() {
        job?.cancel()
    }

}