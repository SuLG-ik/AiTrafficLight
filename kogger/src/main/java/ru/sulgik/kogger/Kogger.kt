@file:JvmName("Kogger")

package ru.sulgik.kogger

import ru.sulgik.kogger.KoggerInstaller.Companion.INSTANCE

@InstallMarker
public fun installKogger(block: KoggerScope.() -> Unit) {
    KoggerInstaller().apply{
        block(createScope())
    }.install()
}

public fun leveledLog(level: KoggerLevel, tag: String?, message: String, cause: Throwable? = null) {
    INSTANCE.logWithoutTag(level, tag, message, cause)
}

public fun info(tag: String?, message: String, cause: Throwable? = null) {
    leveledLog(KoggerLevel.INFO, tag, message, cause)
}

public fun info(message: String, cause: Throwable? = null) {
    info(null, message, cause)
}

public fun debug(tag: String?, message: String, cause: Throwable? = null) {
    leveledLog(KoggerLevel.DEBUG, tag, message, cause)
}

public fun debug(message: String, cause: Throwable? = null) {
    debug(null, message, cause)
}

public fun warning(tag: String?, message: String, cause: Throwable? = null) {
    leveledLog(KoggerLevel.WARNING, tag, message, cause)
}

public fun warning(message: String, cause: Throwable?) {
    warning(null, message, cause)
}

public fun error(tag: String?, message: String, cause: Throwable? = null) {
    leveledLog(KoggerLevel.ERROR, tag, message, cause)
}

public fun error(message: String, cause: Throwable? = null) {
    error(null, message, cause)
}

public fun verbose(tag: String?, message: String, cause: Throwable? = null) {
    leveledLog(KoggerLevel.VERBOSE, tag, message, cause)
}

public fun verbose(message: String, cause: Throwable? = null) {
    verbose(null, message, cause)
}

public abstract class Kogger {

    public fun getTag(tag: String?): String {
        return tag ?: "empty_tag"
    }

    internal fun logWithoutTag(
        level: KoggerLevel,
        tag: String?,
        message: String,
        cause: Throwable?,
    ) {
        log(level, getTag(tag), message, cause)
    }

    abstract fun log(level: KoggerLevel, tag: String, message: String, cause: Throwable?)

}
