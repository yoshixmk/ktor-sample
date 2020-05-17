package yoshixmk.databases.repository

import yoshixmk.domains.irepository.IMemoRepository
import yoshixmk.domains.memos.Memo

class MemoRepository : IMemoRepository {
    override fun findById(id: Int): Memo? {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Memo> =
        findAllIterable().map { m -> Memo(m.id.value, m.subject) }

    override fun findAllSortedById(): List<Memo> =
        findAllIterable().sortedBy { it.id }
            .map { m -> Memo(m.id.value, m.subject) }

    private fun findAllIterable() = yoshixmk.databases.dao.Memo.all()

    override fun create(memo: Memo): Memo {
        TODO("Not yet implemented")
    }

    override fun update(memo: Memo): Memo? {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Int): Long {
        TODO("Not yet implemented")
    }
}
