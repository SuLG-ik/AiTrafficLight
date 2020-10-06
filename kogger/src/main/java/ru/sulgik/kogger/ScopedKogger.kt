package ru.sulgik.kogger

internal class ScopedKogger(val toLog: (KoggerMessage) -> Unit = {}) : Kogger() {

    override fun log(level: KoggerLevel, tag: String, message: String, cause: Throwable?) {
        toLog(KoggerMessage(level, tag, message, cause))
    }

}