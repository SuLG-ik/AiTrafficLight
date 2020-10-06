package ru.sulgik.aitrafficlights.model

import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject
import kotlin.math.round

class FirebaseModelsRepository @Inject constructor(
    private val storage: FirebaseStorage,
) : RemoteModelsStorage {

    companion object {
        const val CORE_MODEL = "core"
    }

    private suspend inline fun fetchModel(
        model: String,
        apiVersion: Long,
        crossinline progressListener: (Int) -> Unit,
    ): FileDownloadTask.TaskSnapshot {
        val task =
            storage.getReference("core-$apiVersion.tflite")
                .getFile(
                    File("$model-model.tflite")
                )

        val listener: (FileDownloadTask.TaskSnapshot) -> Unit = {
            progressListener(round(100.0 * it.bytesTransferred / it.totalByteCount).toInt())
        }

        return try {
            task.addOnProgressListener(listener)
                .await()
        } catch (t: Throwable) {
            task.removeOnProgressListener(listener)
            throw t
        }
    }

    override suspend fun fetchCoreModel(
        apiVersion: Long,
        progressListener: (Int) -> Unit,
    ): FileDownloadTask.TaskSnapshot? {
        return fetchModel("core", apiVersion, progressListener)
    }

}