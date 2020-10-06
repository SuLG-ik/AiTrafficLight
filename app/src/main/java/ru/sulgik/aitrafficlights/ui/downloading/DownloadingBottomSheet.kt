package ru.sulgik.aitrafficlights.ui.downloading

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.dialogViewBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import ru.sulgik.aitrafficlights.R
import ru.sulgik.aitrafficlights.databinding.FragmentDownloadingBinding
import ru.sulgik.aitrafficlights.vm.DownloadingViewModel
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty



@AndroidEntryPoint
class DownloadingBottomSheet : BottomSheetDialogFragment() {

    val downloading: DownloadingViewModel by viewModels()

    val binding: FragmentDownloadingBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_downloading, container, false)
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.
    }

}