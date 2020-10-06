package ru.sulgik.aitrafficlights.metadata

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import ru.sulgik.aitrafficlights.ktx.asFlow
import javax.inject.Inject

class FirebaseModelsMetadataRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
) : RemoteModelsMetadataRepository {

    override val generalModelsMetadataStream: Flow<ModelMetadata> = firestore.collection("stable").document("general").asFlow()

    override suspend fun metadata(): ModelMetadata {
        return awaitMetadata("general").toObject()!!
    }

    override suspend fun coreApiVersion(): Long {
        return awaitMetadata("core")[API_VERSION] as Long
    }

    private suspend fun awaitMetadata(model: String): DocumentSnapshot {
        return firestore.collection("stable").document(model).get().await()
    }

    companion object {
        val API_VERSION: String = "apiVersion"
        val DISPLAY_NAME: String = "displayVersion"
    }

}