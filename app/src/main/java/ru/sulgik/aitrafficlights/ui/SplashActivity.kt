package ru.sulgik.aitrafficlights.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.splash_activity.*
import ru.sulgik.aitrafficlights.AITrafficLightsActivity
import ru.sulgik.aitrafficlights.R
import ru.sulgik.aitrafficlights.data.res.DownloadingState
import ru.sulgik.aitrafficlights.metadata.LocalMetadata
import ru.sulgik.aitrafficlights.vm.DownloadingViewModel
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AITrafficLightsActivity() {

    val detectingModelsViewModel: DownloadingViewModel by viewModels()

    @Inject
    lateinit var localData: LocalMetadata

    @Inject
    lateinit var remoteMetadata: RemoteModelsMetadataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        localData.generalSettingsFlow.collect(this) {
            Log.d(TAG, "general flow $it")
        }

        localData.modelsVersionsFlow.collect(this) {
            Log.d(TAG, "versions $it")
        }

        remoteMetadata.stableMetadataFlow.collect(this) {
            Log.d(TAG, "models $it")
        }

        created_by_image.setOnClickListener {
            detectingModelsViewModel.updateModels().collect(this) {
                when (it) {
                    is DownloadingState.InProgress -> {
                        Log.d(TAG, "progress = ${it.progress}")
                    }
                    is DownloadingState.Success -> {
                        Log.d(TAG, "success")
                    }
                    is DownloadingState.Failure -> {
                        Log.d(TAG, "failure", it.exception)
                    }
                    is DownloadingState.Canceled -> {
                        Log.d(TAG, "canceled")
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "SplashActivityTag"
    }
}