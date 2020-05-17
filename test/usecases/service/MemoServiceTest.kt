package yoshixmk.usecases.service

import io.ktor.util.KtorExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.koin.test.AutoCloseKoinTest
import yoshixmk.domains.irepository.IMemoRepository
import yoshixmk.domains.memos.Memo
import yoshixmk.testKoinModules
import kotlin.test.Test

@KtorExperimentalAPI
class MemoServiceTest : AutoCloseKoinTest() {

    init {
        startKoin { modules(testKoinModules) }
    }

    private val repository: IMemoRepository by inject()

    @Test
    fun findAllSortedByIdでデータが取得できること() {
        val actual = repository.findAllSortedById()
        assert(
            actual == listOf(
                Memo(1, "memo1"),
                Memo(2, "memo2")
            )
        )
    }

}
