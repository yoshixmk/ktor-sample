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

    @Test
    fun createでデータが作成できること() {
        val actual = service.create("memo3")
        assert(
            actual == Memo(3, "memo3")
        )
    }

    @Test
    fun updateでデータが更新できること() {
        val actual = service.update(1, "memo1 updated")
        assert(
            actual == Memo(1, "memo1 updated")
        )
    }

    @Test
    fun deleteByIdでデータが削除できること() {
        val actual = service.deleteById(1)
        assert(actual == Memo(1, "memo1"))
    }
}
