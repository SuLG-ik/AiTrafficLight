package ru.sulgik.aitrafficlights.metadata

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class DataStoreLocalMetadataRepository @Inject constructor(@ApplicationContext private val context: Context) :
    LocalModelsMetadataRepository {


    private val generalDataStore = context.createDataStore("general_settings")
    private val modelsDataStore = context.createDataStore("models_versions")

    override val generalModelsMetadataStream: Flow<ModelMetadata> = generalDataStore.data
        .catch { cause ->
            if (cause is IOException) {
                emit(emptyPreferences())
            } else {
                throw cause
            }
        }.map {

            val version = it[API_VERSION] ?: 0

            val name = it[DISPLAY_NAME] ?: "uninstalled"


            ModelMetadata(
                apiVersion = version,
                displayVersion = name,
            )
        }

    override suspend fun metadata(): ModelMetadata {
        val data = modelsDataStore.edit {  }
        return ModelMetadata(data[API_VERSION], data[DISPLAY_NAME])
    }

    override suspend fun updateMetadata(metadata: ModelMetadata) {
        modelsDataStore.edit {
            it[API_VERSION] = metadata.apiVersion
            it[DISPLAY_NAME] = metadata.displayVersion
        }
    }

    override suspend fun coreApiVersion(): Long = modelsDataStore.edit {  }[CORE_API_VERSION] ?: 0

    override suspend fun updateCoreVersion(apiVersion: Long) {
        modelsDataStore.edit {
            it[CORE_API_VERSION] = apiVersion
        }
    }

    companion object {
        val API_VERSION = preferencesKey<Long>("api_version")
        val DISPLAY_NAME = preferencesKey<String>("display_name")
        val CORE_API_VERSION = preferencesKey<Long>("core_api_version")
    }

}