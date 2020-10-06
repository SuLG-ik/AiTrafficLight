package ru.sulgik.aitrafficlights.provider

import kotlinx.coroutines.CoroutineScope
import ru.sulgik.aitrafficlights.data.res.DownloadingState
import ru.sulgik.core.ObservableCase

interface FetchModelsProvider {

    fun getFetchAllModelsCase(scope: CoroutineScope): ObservableCase<DownloadingState, Int>

}