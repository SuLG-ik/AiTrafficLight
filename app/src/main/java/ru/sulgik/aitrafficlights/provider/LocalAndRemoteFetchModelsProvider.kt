package ru.sulgik.aitrafficlights.provider

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.sulgik.aitrafficlights.case.LocalAndRemoteFetchAllModelsUseCase
import ru.sulgik.aitrafficlights.data.res.DownloadingState
import ru.sulgik.aitrafficlights.metadata.LocalModelsMetadataRepository
import ru.sulgik.aitrafficlights.metadata.RemoteModelsMetadataRepository
import ru.sulgik.aitrafficlights.model.RemoteModelsStorage
import ru.sulgik.core.ObservableCase
import javax.inject.Inject

class LocalAndRemoteFetchModelsProvider @Inject constructor(
    private val storage: RemoteModelsStorage,
    private val local: LocalModelsMetadataRepository,
    private val remote: RemoteModelsMetadataRepository,
) : FetchModelsProvider {
    override fun getFetchAllModelsCase(scope: CoroutineScope): ObservableCase<DownloadingState, Int> {
        return ObservableCase(scope,
            Dispatchers.IO,
            LocalAndRemoteFetchAllModelsUseCase(storage, remote, local))
    }
}