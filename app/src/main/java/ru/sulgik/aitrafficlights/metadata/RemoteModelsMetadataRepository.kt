package ru.sulgik.aitrafficlights.metadata

interface RemoteModelsMetadataRepository{

    suspend fun fetchCoreMetadata(): ModelMetadata

}