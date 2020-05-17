package yoshixmk.usecases.service

import io.ktor.util.KtorExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.koin.test.AutoCloseKoinTest
import yoshixmk.domains.memos.Memo
import yoshixmk.testServiceModules
import kotlin.test.Test

@KtorExperimentalAPI
class MemoServiceTest : AutoCloseKoinTest() {

    init {
        startKoin { modules(testServiceModules) }
    }

    private val service: IMemoService by inject()

    @Test
    fun findAllSortedByIdでデータが取得できること() {
        val actual = service.findAllSortedById()
        assert(
            actual == listOf(
                Memo(1, "memo1"),
                Memo(2, "memo2")
            )
        )
    }

    @Test
    fun findByIdでデータが取得できること() {
        val actual = service.findById(1)
        assert(
            actual == Memo(1, "memo1")
        )
    }

//    @Test
//    fun findByIdでデータが取得できること() {
//        val actual = repository.create()
//        assert(
//            actual == Memo(1, "memo1")
//        )
//    }
}
