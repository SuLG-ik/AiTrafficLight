package ru.sulgik.aitrafficlights.metadata

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.lang.IllegalStateException

@Parcelize
data class ModelMetadata(val version: Long = throw IllegalStateException("server doesn't get version"), val maxSize: Long = throw IllegalStateException("server doesn't get maxSize")) : Parcelable