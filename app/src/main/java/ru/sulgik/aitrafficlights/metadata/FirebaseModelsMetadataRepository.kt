package ru.sulgik.aitrafficlights.metadata

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseModelsMetadataRepository @Inject constructor(private val firestore: FirebaseFirestore) : RemoteModelsMetadataRepository {

    override suspend fun fetchCoreMetadata(): ModelMetadata {
        return awaitMetadata("core").toObject()!!
    }

    private suspend fun awaitMetadata(model: String): DocumentSnapshot {
        return firestore.collection("public").document(model).get().await()
    }
}