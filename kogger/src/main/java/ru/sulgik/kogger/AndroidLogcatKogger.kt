package ru.sulgik.kogger

import android.util.Log

class AndroidLogcatKogger() : Kogger() {

    override fun log(level: KoggerLevel, tag: String, message: String, cause: Throwable?) {
        when(level) {
            KoggerLevel.INFO -> Log.i(tag, message, cause)
            KoggerLevel.DEBUG -> Log.d(tag, message, cause)
            KoggerLevel.ERROR -> Log.d(tag, message, cause)
            KoggerLevel.VERBOSE -> Log.v(tag, message, cause)
            KoggerLevel.WARNING -> Log.w(tag, message, cause)
        }
    }

}