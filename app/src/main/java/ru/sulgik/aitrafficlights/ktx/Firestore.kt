package ru.sulgik.aitrafficlights.ktx

import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

inline fun <reified T> DocumentReference.asFlow(): Flow<T> {
    return callbackFlow<T> {
        val registration = addSnapshotListener{ value, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (value != null) {
                offer(value.toObject<T>() ?: error("value is not ${T::class.simpleName}"))
            }
        }
        awaitClose { registration.remove() }
    }
}

inline fun <reified T> CollectionReference.asFlow(): Flow<Array<T>> {
    return callbackFlow<Array<T>> {
        val registration = addSnapshotListener{ values, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val list = mutableListOf<T>()
            values?.forEach {
                val obj = it.toObject<T>()
                if (obj != null)
                    list.add(obj)
            }
            offer(list.toTypedArray())
        }

        awaitClose { registration.remove() }
    }
}