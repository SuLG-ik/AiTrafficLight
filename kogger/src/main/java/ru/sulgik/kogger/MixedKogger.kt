package ru.sulgik.kogger

class MixedKogger : Kogger() {

    private val koggers  = mutableListOf<Kogger>()

    override fun log(level: KoggerLevel, tag: String, message: String, cause: Throwable?) {
        koggers.forEach {
            it.log(level, tag, message, cause)
        }
    }

    public fun addKogger(kogger: Kogger) {
        koggers.add(kogger)
    }
}