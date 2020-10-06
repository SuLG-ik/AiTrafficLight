package ru.sulgik.aitrafficlights.data.res

import ru.sulgik.aitrafficlights.data.req.Model

sealed class DownloadingState {

    class InProgress(val model: Model, val progress: Int) : DownloadingState()
    object Success : DownloadingState()

}

