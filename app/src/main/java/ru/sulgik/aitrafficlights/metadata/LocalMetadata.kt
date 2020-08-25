package ru.sulgik.aitrafficlights.metadata

import android.content.Context

const val VERSIONS = "version0"

fun Context.getCoreVersion(): Long {
    return getSharedPreferences(VERSIONS, Context.MODE_PRIVATE).getLong("core_version", 0)
}