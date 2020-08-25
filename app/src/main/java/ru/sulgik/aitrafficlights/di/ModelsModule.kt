package ru.sulgik.aitrafficlights.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.sulgik.aitrafficlights.metadata.FirebaseModelsMetadataRepository
import ru.sulgik.aitrafficlights.metadata.RemoteModelsMetadataRepository
import ru.sulgik.aitrafficlights.models.FirebaseModelsRepository
import ru.sulgik.aitrafficlights.models.ModelsRepository
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class ModelsModule {

    @Binds
    abstract fun bindsRemoteModelsMetadataRepository(impl: FirebaseModelsMetadataRepository): RemoteModelsMetadataRepository

    @Binds
    abstract fun bindsModelsRepository(impl: FirebaseModelsRepository): ModelsRepository

 }