package yoshixmk.databases.repository

import yoshixmk.domains.irepository.IMemoRepository
import yoshixmk.domains.memos.Memo

class MockMemoRepository : IMemoRepository {
    private val data = listOf(
        Memo(1, "memo1"),
        Memo(2, "memo2")
    )

    override fun findById(id: Int): Memo? =
        data.find { m -> m.id == id }

    override fun findAll(): List<Memo> = data

    override fun findAllSortedById(): List<Memo> =
        findAll().sortedBy { m -> m.id }

    override fun create(memo: Memo): Memo =
        Memo(3, "memo3")

    override fun update(memo: Memo): Memo? {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Int): Long {
        TODO("Not yet implemented")
    }

}
