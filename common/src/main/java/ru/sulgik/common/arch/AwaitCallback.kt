package ru.sulgik.common.arch


/**
 * Base SAM suspend callback. Thread will be paused and resumed wish completed result
 * @author Vova Nenashkin
 * @since 1.0
 */
interface AwaitCallback<T> {

    /**
     * Suspend method to resume with result or exception
     * @return result of launching
     * @throws Throwable is exception during launching
     */
    @Throws(Throwable::class)
    suspend fun await() : T

}