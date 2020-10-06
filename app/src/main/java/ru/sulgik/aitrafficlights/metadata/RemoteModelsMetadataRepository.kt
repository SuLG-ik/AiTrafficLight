package ru.sulgik.aitrafficlights.metadata

import kotlinx.coroutines.flow.Flow
import ru.sulgik.aitrafficlights.settings.ModelsApiVersions

interface RemoteModelsMetadataRepository {

    suspend fun coreApiVersion(): Long
    suspend fun metadata(): ModelMetadata

    val generalModelsMetadataStream: Flow<ModelMetadata>


}