package ru.sulgik.kogger

@Deprecated(
    message = "don't use install() inside KoggerContext",
    level = DeprecationLevel.ERROR,
)
@InstallMarker
fun KoggerScope.install() {}

@Deprecated(
    message = "don't use install() inside KoggerMessage",
    level = DeprecationLevel.ERROR,
)
@InstallMarker
fun KoggerMessage.install() {}
