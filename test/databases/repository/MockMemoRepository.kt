package yoshixmk.databases.repository

import yoshixmk.domains.irepository.IMemoRepository
import yoshixmk.domains.memos.Memo
import yoshixmk.domains.memos.MemoSubject

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

    override fun create(subject: MemoSubject): Memo =
        Memo(3, "memo3")

    override fun update(memo: Memo): Memo? =
        if (memo.id == 1) Memo(1, "memo1 updated")
        else null

    override fun deleteById(id: Int): Memo? =
        if (id == 1) Memo(1, "memo1")
        else null
}
