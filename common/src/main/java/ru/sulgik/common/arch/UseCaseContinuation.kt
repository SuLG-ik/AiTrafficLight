package ru.sulgik.common.arch

/**
 * Immutable continuation to collect [param] and callbacks
 * @author Vova Nenashkin
 * @since 1.0
 * @see UseCase
 */
interface UseCaseContinuation<T,P>{

    /**
     * Immutable param received via [ObservableUseCaseContinuation]
     */
    val param : P

    /**
     * Send success event
     * @param result is success data to send it to observers
     */
    fun emitResult(result: T)

    /**
     * Send loading event
     */
    fun emitLoading()

    /**
     * Send failure event
     * @param t is failure data to send it to observers
     */
    fun emitError(t: Throwable)

    /**
     * Action will be invoked on cancel
     */
    fun invokeOnCancel(action : () -> Unit)

}