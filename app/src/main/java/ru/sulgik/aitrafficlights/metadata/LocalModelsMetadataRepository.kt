package ru.sulgik.aitrafficlights.metadata

import kotlinx.coroutines.flow.Flow
import ru.sulgik.aitrafficlights.settings.ModelsApiVersions

interface LocalModelsMetadataRepository {

    suspend fun updateMetadata(metadata: ModelMetadata)

    suspend fun coreApiVersion(): Long
    suspend fun updateCoreVersion(apiVersion: Long)

    val generalModelsMetadataStream: Flow<ModelMetadata>

    suspend fun metadata(): ModelMetadata

}