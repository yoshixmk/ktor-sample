package yoshixmk.domains.irepository

import yoshixmk.domains.memos.Memo
import yoshixmk.domains.memos.MemoSubject

interface IMemoRepository {
    fun findById(id: Int): Memo?

    fun findAll(): List<Memo>

    fun findAllSortedById(): List<Memo>

    fun create(subject: MemoSubject): Memo

    fun update(memo: Memo): Memo?

    fun deleteById(id: Int): Memo?
}
