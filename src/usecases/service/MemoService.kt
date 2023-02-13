package yoshixmk.usecases.service

import yoshixmk.domains.irepository.IMemoRepository
import yoshixmk.domains.memos.Memo
import yoshixmk.domains.memos.MemoSubject

interface IMemoService {
    fun findById(id: Int): Memo?

    fun findAllSortedById(): List<Memo>

    fun create(subject: String): Memo

    fun update(id: Int, subject: String): Memo?

    fun deleteById(id: Int): Memo?
}

class MemoService(private val repository: IMemoRepository) : IMemoService {
    override fun findById(id: Int): Memo? =
        repository.findById(id)

    override fun findAllSortedById(): List<Memo> =
        repository.findAllSortedById()

    override fun create(subject: String): Memo =
        repository.create(MemoSubject(subject))

    override fun update(id: Int, subject: String): Memo? =
        repository.update(Memo(id, subject))

    override fun deleteById(id: Int): Memo? =
        repository.deleteById(id)
}
