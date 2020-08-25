package ru.sulgik.common

import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import ru.sulgik.common.TestDispatchers.PostThread
import ru.sulgik.common.arch.*
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@DisplayName("test observable cases")
class CasesTest {

    val coroutineScope = CoroutineScope(newSingleThreadContext("CasesTestScope") + SupervisorJob())
    val postScope = CoroutineScope(newSingleThreadContext("Post Scope") + SupervisorJob())

    @BeforeEach
    fun start(){
        TestDispatchers.setupMain()
    }

    @AfterEach
    fun restart(){
        TestDispatchers.resetWorker()
        TestDispatchers.cleanupMain()
        coroutineScope.coroutineContext.cancelChildren()
        postScope.coroutineContext.cancelChildren()
    }

    @Nested
    @DisplayName("test success cases")
    inner class SuccessTest {

        private val res : String = "46546"
        private val req : String = "87985"

        private val expected = req + res


        private val case = ObservableCase<String, String>(coroutineScope, TestDispatchers.Worker, postScope, PostThread) {
            delay(TASK_DURATION)
            it.emitResult(it.param + res)
        }

        @DisplayName("simple test $REPEATING_TIMES times")
        @RepeatedTest(value = REPEATING_TIMES, name = "repeated test success case")
        @Timeout(value = MAX_TIMEOUT)
        fun simple(){
            runBlocking {
                case.execute(req)

                val ans = case.await()
                Assertions.assertEquals(expected,ans)
            }
        }

    }

    @Nested
    inner class FailureTest {


        private val expected = Exception("normal exception")
        private val case = ObservableCase<String, Exception>(coroutineScope, TestDispatchers.Worker, postScope, PostThread){
            delay(TASK_DURATION)
            throw it.param
        }

        @DisplayName("simple test $REPEATING_TIMES times")
        @RepeatedTest(value = REPEATING_TIMES, name = "repeated test failure case")
        @Timeout(value = MAX_TIMEOUT)
        fun failure(){
            runBlocking{
                case.execute(expected)

                assertThrows<java.lang.Exception> { runBlocking { case.await() } }
            }
        }
    }

    @Nested
    @DisplayName("test canceled cases")
    inner class CancelTest {

        private val context = newSingleThreadContext("Cancelable context")
        private val case = ObservableCase<String, String>(coroutineScope, context, postScope, PostThread) {
            delay(TASK_DURATION)
            throw RuntimeException("must be canceled")
        }

        @DisplayName("success scope must be canceled via default canceling")
        @RepeatedTest(value = REPEATING_TIMES, name = "repeated test canceled case via default")
        @Timeout(value = MAX_TIMEOUT)
        fun cancelViaCase(){
            runBlocking {
                case.execute("")

                launch {
                    delay(CANCEL_AFTER)
                    case.cancel()
                }


                assertThrows<CancellationException> { runBlocking { case.await() } }
            }
        }

        @DisplayName("success scope must be canceled via scope canceling")
        @Test
        @Timeout(value = MAX_TIMEOUT)
        fun cancelViaScope() {
            runBlocking {
                case.execute("")

                launch {
                    delay(CANCEL_AFTER)
                    coroutineScope.coroutineContext.cancel()
                }

                assertThrows<CancellationException> { runBlocking { case.await() } }
            }
        }

    }

    companion object {

        private const val TASK_DURATION = 200L

        private const val MAX_TIMEOUT = 1L

        private const val CANCEL_AFTER = 50L

        private const val REPEATING_TIMES = 2
    }

}