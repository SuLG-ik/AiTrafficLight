package ru.sulgik.aitrafficlights.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ru.sulgik.aitrafficlights.metadata.DataStoreLocalMetadataRepository
import ru.sulgik.aitrafficlights.metadata.FirebaseModelsMetadataRepository
import ru.sulgik.aitrafficlights.metadata.LocalModelsMetadataRepository
import ru.sulgik.aitrafficlights.metadata.RemoteModelsMetadataRepository
import ru.sulgik.aitrafficlights.model.FirebaseModelsRepository
import ru.sulgik.aitrafficlights.model.RemoteModelsStorage
import ru.sulgik.aitrafficlights.provider.FetchModelsProvider
import ru.sulgik.aitrafficlights.provider.LocalAndRemoteFetchModelsProvider

@Module
@InstallIn(ApplicationComponent::class)
abstract class ModelsModule {

    @Binds
    abstract fun bindsSettingsRepository(impl: DataStoreLocalMetadataRepository): LocalModelsMetadataRepository

    @Binds
    abstract fun bindsRemoteModelsMetadataRepository(impl: FirebaseModelsMetadataRepository): RemoteModelsMetadataRepository

    @Binds
    abstract fun bindsModelsRepository(impl: FirebaseModelsRepository): RemoteModelsStorage

    @Binds
    abstract fun bindsFetchModelsProvider(impl: LocalAndRemoteFetchModelsProvider): FetchModelsProvider

}