package yoshixmk.databases.repository

import org.jetbrains.exposed.sql.transactions.transaction
import yoshixmk.domains.irepository.IMemoRepository
import yoshixmk.domains.memos.Memo
import yoshixmk.domains.memos.MemoSubject

class MemoRepository : IMemoRepository {
    override fun findById(id: Int): Memo? =
        transaction {
            yoshixmk.databases.dao.Memo.findById(id)?.let { m -> Memo(m.id.value, m.subject) }
        }

    override fun findAll(): List<Memo> =
        transaction {
            findAllIterable().map { m -> Memo(m.id.value, m.subject) }
        }

    override fun findAllSortedById(): List<Memo> =
        transaction {
            findAllIterable().sortedBy { it.id }
                .map { m -> Memo(m.id.value, m.subject) }
        }

    private fun findAllIterable() = yoshixmk.databases.dao.Memo.all()

    override fun create(subject: MemoSubject): Memo =
        transaction {
            yoshixmk.databases.dao.Memo.new { this.subject = subject.subject }
                .let { m -> Memo(m.id.value, m.subject) }
        }

    override fun update(memo: Memo): Memo? {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Int): Memo? {
        TODO("Not yet implemented")
    }
}
