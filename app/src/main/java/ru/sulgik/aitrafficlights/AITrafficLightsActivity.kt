package ru.sulgik.aitrafficlights

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.flow.collect as flowCollect

abstract class AITrafficLightsActivity : AppCompatActivity()

inline fun <T> Flow<T>.collect(
    owner: LifecycleOwner,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    crossinline collector: suspend (T) -> Unit,
) {
    owner.lifecycleScope.launch(context, start) {
        this@collect.flowCollect(collector)
    }
}