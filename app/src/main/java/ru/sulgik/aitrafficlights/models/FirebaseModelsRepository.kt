package ru.sulgik.aitrafficlights.models

import android.content.Context
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import ru.sulgik.aitrafficlights.metadata.RemoteModelsMetadataRepository
import javax.inject.Inject

class FirebaseModelsRepository @Inject constructor(
    private val storage: FirebaseStorage,
    private val metadataRepository: RemoteModelsMetadataRepository
) : ModelsRepository {

    override suspend fun fetchCoreModel(): ByteArray {
        return storage.getReference("core_model.tflite")
            .getBytes(
                metadataRepository
                    .fetchCoreMetadata()
                    .maxSize
            ).await()
    }

}