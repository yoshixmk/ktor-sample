package yoshixmk.usecases.service

import yoshixmk.domains.irepository.IMemoRepository
import yoshixmk.domains.memos.Memo

interface IMemoService {
    fun findById(id: Int): Memo?

    fun findAllSortedById(): List<Memo>

    fun create(subject: String): Memo

    fun update(id: Int, subject: String): Memo?

    fun deleteById(id: Int): Long
}

class MemoService(private val repository: IMemoRepository) : IMemoService {
    override fun findById(id: Int): Memo? =
        repository.findById(id)

    override fun findAllSortedById(): List<Memo> =
        repository.findAllSortedById()

    override fun create(subject: String): Memo {
        TODO("Not yet implemented")
    }

    override fun update(id: Int, subject: String): Memo? {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Int): Long {
        TODO("Not yet implemented")
    }
}
