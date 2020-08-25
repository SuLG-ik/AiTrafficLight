package ru.sulgik.common.event

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlin.experimental.ExperimentalTypeInference


typealias MutableFlowEvent<T> = MutableStateFlow<EventResult<T>>
typealias FlowEvent<T> = Flow<EventResult<T>>
typealias FlowEventCollector <T> = FlowCollector<EventResult<T>>

fun <T> flowEvent(block : suspend FlowEventCollector<T>.() -> Unit): FlowEvent<T> {
    return flow(block)
}

suspend inline fun <T> FlowEventCollector<T>.emitSuccess(result : T){
    emit(EventResult.Successful(result))
}

suspend inline fun <T> FlowEventCollector<T>.emitFailure(exception: Exception){
    emit(EventResult.Failure(exception))
}

fun <T> MutableFlowEvent(value : T) = MutableStateFlow<EventResult<T>>(EventResult.Successful(value))
fun <T> MutableFlowEvent(e : Exception) = MutableStateFlow<EventResult<T>>(EventResult.Failure<T>(e))
fun <T> MutableFlowEvent() = MutableStateFlow<EventResult<T>>(EventResult.Wishing<T>())

fun <T> MutableFlowEvent<T>.postSuccess(result: T) { value = EventResult.Successful(result) }
fun <T> MutableFlowEvent<T>.postFailure(e: Throwable) { value = EventResult.Failure(e) }
fun <T> MutableFlowEvent<T>.postCancel() { value = EventResult.Cancel() }