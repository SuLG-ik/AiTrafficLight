package ru.sulgik.core

sealed class Result<T> {

    open val result: T? = null
    open val cause: Throwable? = null

    class Successful<T>(override val result: T) : Result<T>()
    class Failure<T>(override val cause: Throwable) : Result<T>()
    class Canceled<T> : Result<T>()

}