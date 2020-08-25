package ru.sulgik.common.event

typealias OnSuccessListener<T> = (result : T) -> Unit
typealias OnFailureListener = (e: Throwable) -> Unit
typealias OnCancelListener = () -> Unit
typealias OnLoadingListener = () -> Unit

sealed class EventResult<T> {
    class Successful <T> (val result : T) : EventResult<T>()
    class Failure <T>(val throwable: Throwable) : EventResult<T>()
    class Wishing <T> : EventResult<T>()
    class Cancel <T> (): EventResult<T>()
    class Loading<T> : EventResult<T>()

    inline val resultOrThrow get() = if (this is Successful<T>) result else throw IllegalStateException("$this is not successful")
    inline val exceptionOrThrow get() = if (this is Failure<T>) throwable else throw IllegalStateException("$this is not failure")

}

inline fun <T> EventResult<T>.doOnSuccess(action : OnSuccessListener<T>){
    if (this is EventResult.Successful<T>){
        action(result)
    }
}

inline fun <T> EventResult<T>.doOnFailed(action : OnFailureListener){
    if (this is EventResult.Failure<T>){
        action(throwable)
    }
}

inline fun <T> EventResult<T>.doOnCanceled(action : OnCancelListener){
    if (this is EventResult.Cancel<T>){
        action()
    }
}

inline fun <T> EventResult<T>.doOnLoading(action : OnLoadingListener){
    if (this is EventResult.Loading<T>){
        action()
    }
}