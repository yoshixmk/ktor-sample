package yoshixmk.domains.irepository

import yoshixmk.domains.memos.Memo

interface IMemoRepository {
    fun findById(id: Int): Memo?

    fun findAll(): List<Memo>

    fun findAllSortedById(): List<Memo>

    fun create(memo: Memo): Memo

    fun update(memo: Memo): Memo?

    fun deleteById(id: Int): Long
}
