package ru.sulgik.aitrafficlights.case

import ru.sulgik.aitrafficlights.data.res.DownloadingState
import ru.sulgik.aitrafficlights.data.req.Model
import ru.sulgik.aitrafficlights.model.RemoteModelsStorage
import ru.sulgik.aitrafficlights.metadata.LocalModelsMetadataRepository
import ru.sulgik.aitrafficlights.metadata.RemoteModelsMetadataRepository
import ru.sulgik.core.UseCase

class LocalAndRemoteFetchAllModelsUseCase(
    private val storage: RemoteModelsStorage,
    private val remote: RemoteModelsMetadataRepository,
    private val local: LocalModelsMetadataRepository,
) : UseCase<DownloadingState, Int>() {

    override suspend fun run(parameter: Int) {
        val localGeneralVersion = local.metadata()
        val remoteGeneralMetadata = remote.metadata()

        if (localGeneralVersion.apiVersion == remoteGeneralMetadata.apiVersion) {
            emitResult(
                DownloadingState.Success
            )
            return
        }

        val localCoreVersion = local.coreApiVersion()
        val remoteCoreVersion = remote.coreApiVersion()

        if (localCoreVersion != remoteCoreVersion) {
            storage.fetchCoreModel(remoteCoreVersion) {
                emitResult(DownloadingState.InProgress(Model.CORE, it))
            }
            local.updateCoreVersion(localCoreVersion)
        }

        local.updateMetadata(remoteGeneralMetadata)

        emitResult(
            DownloadingState.Success
        )
    }

}