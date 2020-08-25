package ru.sulgik.common.arch

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.sulgik.common.event.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

typealias OnSuccessListener<T> = (T) -> Unit
typealias OnFailureListener =  (Throwable) -> Unit
typealias OnCancelListener = () -> Unit

/**
 * Class to implement advanced multi-thread controlling
 * @param launchScope will be used to async launch [UseCase]
 * @param launchContext there will be coroutine will be started; default: [EmptyCoroutineContext]
 * @param useCase is an coroutine executable
 * @author Vladimir Nenashkin
 * @since 1.0
 */
class ObservableCase <T, P> (
    internal val launchScope : CoroutineScope,
    internal val launchContext : CoroutineContext = EmptyCoroutineContext,
    private val useCase: UseCase<T, P>
) : AwaitCallback<T> {

    /**
     * Mutable value to know does [UseCase] is executed
     * @see isExecuted
     */
    private val _isExecuted = MutableStateFlow(false)

    /**
     * Immutable value to know does [UseCase] is executed
     * @see isExecuted
     */
    val isExecuted : StateFlow<Boolean>
        get() = _isExecuted

    /**
     * Mutable value to know can [UseCase] be executed
     * @see isExecutable
     */
    private val _isExecutable = MutableStateFlow(true)

    /**
     * Immutable value to know can [UseCase] be executed
     * @see isExecuted
     */
    val isExecutable : StateFlow<Boolean>
        get() = _isExecutable

    /**
     * Listener to implement success event of [AwaitCallback]
     * @see await
     */
    private var awaitSuccessListener : OnSuccessListener<T> = {}

    /**
     * Listener to implement failure event of [AwaitCallback]
     * @see await
     */
    private var awaitFailureListener : OnFailureListener = {}

    /**
     * Listener to implement cancel event of [AwaitCallback]
     * @see await
     */
    private var awaitCancelListener : OnCancelListener = {}

    /**
     * Mutable stream to notify observers about events
     * @see flowEvent
     */
    private val _flowEvent : MutableFlowEvent<T> = MutableFlowEvent<T>()

    /**
     * Immutable stream to notify observers about events
     */
    val flowEvent : FlowEvent<T>
        get() = _flowEvent

    /**
     * Function to cancel [UseCase]
     * @see UseCase
     * @see execute
     */
    fun cancel() {
        useCase.cancel()
    }

    /**
     * Entry point to async launch job via [ObservableUseCaseContinuation
     * @param handler will be invoked at excepted situations
     * @param block is coroutine block to async launch
     * @return launched job
     * @see execute
     */
    internal fun launch(
        handler : CoroutineExceptionHandler,
        block : suspend CoroutineScope.() -> Unit
    ): Job {
        return launchScope.launch(context = launchContext + handler, block = block)
    }

    /**
     * Entry point to execute parametrized job
     * @param param is needed arguments
     * @see await
     * @return callback
     */
    fun execute(param: P): AwaitCallback<T> {
        if (isExecuted.value || !isExecutable.value) {
            return this
        }
        _isExecuted.value = true

        useCase.execute(ObservableUseCaseContinuation(this, param))
        return this
    }

    /**
     * Notify all observers about success event
     * @param result will be sent as success event to observer
     * @see execute
     */
    internal fun emitResult(result: T) {
        awaitSuccessListener.invoke(result)
        _flowEvent.postSuccess(result)
    }

    /**
     * Notify all observers about success event
     * @param t will be sent as failure event to observer
     * @see execute
     */
    internal fun emitError(t: Throwable) {
        awaitFailureListener.invoke(t)
        _flowEvent.postFailure(t)
    }

    /**
     * Notify all observers about cancel event
     * @see execute
     */
    internal fun emitCancel() {
        awaitCancelListener.invoke()
        _flowEvent.postCancel()
    }

    /**
     * Notify all observers about loading job
     * @see execute
     */
    internal fun emitLoading(){
        _flowEvent.value = EventResult.Loading()
    }

    /**
     * Emit end of execution
     * @see isExecuted
     * @see execute
     */
    internal fun emitExecutionCompletion() {
        _isExecuted.value = false
    }

    /**
     * Remove all listeners
     * @see await
     */
    private fun removeAwaitingListeners(){
        awaitCancelListener = {}
        awaitSuccessListener = {}
        awaitFailureListener = {}
    }

    /**
     * Implementation of [AwaitCallback]
     * @return result of launching
     * @throws Throwable is exception during launching
     * @see AwaitCallback
     * @see execute
     */
    @Throws(Throwable::class)
    override suspend fun await(): T {
        return suspendCancellableCoroutine { cont ->

            awaitCancelListener = {
                removeAwaitingListeners()
                cont.cancel()
            }
            awaitSuccessListener = {
                removeAwaitingListeners()
                cont.resume(it)
            }
            awaitFailureListener = {
                removeAwaitingListeners()
                cont.resumeWithException(it)
            }

            cont.invokeOnCancellation {
                removeAwaitingListeners()
                cancel()
            }
        }

    }

    companion object {

        /**
         * Default Dispatcher to post events
         */
        val POST_DISPATCHER = Dispatchers.Main
    }
}
