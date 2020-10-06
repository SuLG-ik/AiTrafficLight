package ru.sulgik.aitrafficlights.metadata

data class ModelMetadata(
    val apiVersion: Long = 0,
    val displayVersion: String = "uninstalled",
) {
    constructor(
        apiVersion: Long?,
        displayVersion: String?,
    ) : this(
        apiVersion?: 0,
        displayVersion ?: "uninstalled",
    )

}