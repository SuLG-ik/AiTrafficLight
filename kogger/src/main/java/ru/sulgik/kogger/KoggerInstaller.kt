package ru.sulgik.kogger

public class KoggerInstaller {

    internal var kogger = MixedKogger()


    public fun createScope(): KoggerScope {
        return KoggerScope(this)
    }

    @InstallMarker
    public fun install() {
        synchronized(this) {
            INSTANCE = kogger
        }
        installKogger {
            case {

            }
        }
    }

    companion object {
        @Volatile
        internal var INSTANCE: Kogger = AndroidLogcatKogger()
    }

}




public class KoggerScope(val installer : KoggerInstaller) {


    @CaseMarker
    public fun case(addedKogger: Kogger) {
        installer.kogger.addKogger(addedKogger)
    }

    @CaseMarker
    public fun case(block: KoggerMessage.() -> Unit) {
        installer. kogger.addKogger(ScopedKogger(block))
    }

}