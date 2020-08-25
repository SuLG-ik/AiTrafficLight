package ru.sulgik.aitrafficlights.models

interface ModelsRepository {

    suspend fun fetchCoreModel(): ByteArray

}