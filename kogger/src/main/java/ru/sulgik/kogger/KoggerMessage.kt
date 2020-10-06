package ru.sulgik.kogger

public data class KoggerMessage(
    public val level: KoggerLevel,
    public val tag: String?,
    public val message: String,
    public val cause: Throwable?,
) {

    @Deprecated(
        message = "don't use case() inside KoggerMessage",
        level = DeprecationLevel.ERROR,
    )
    @CaseMarker
    public fun case(block: KoggerMessage.() -> Unit) {}


    @Deprecated(
        message = "don't use case() inside KoggerMessage",
        level = DeprecationLevel.ERROR,
    )
    @CaseMarker
    public fun case(addedKogger: Kogger) {}

}