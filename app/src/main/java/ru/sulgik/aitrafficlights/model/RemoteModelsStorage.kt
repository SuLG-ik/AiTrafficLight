package ru.sulgik.aitrafficlights.model

import com.google.firebase.storage.FileDownloadTask

interface RemoteModelsStorage {

    suspend fun fetchCoreModel(apiVersion: Long, progressListener: (Int) -> Unit = {}): FileDownloadTask.TaskSnapshot?

}