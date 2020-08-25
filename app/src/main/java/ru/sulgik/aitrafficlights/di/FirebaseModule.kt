package ru.sulgik.aitrafficlights.di

import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class FirebaseModule {

    @Provides
    @Singleton
    fun provideApp() = Firebase.app

    @Provides
    @Singleton
    fun provideFirestore(app: FirebaseApp) = Firebase.firestore(app)

    @Provides
    @Singleton
    fun provideStorage(app: FirebaseApp) = Firebase.storage(app)

}