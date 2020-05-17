package yoshixmk.usecases.service

import org.koin.core.KoinComponent
import yoshixmk.domains.memos.Memo

interface IMemoService : KoinComponent {
    fun findById(userId: Int): Memo?

    fun findAllSortedById(): List<Memo>

    fun create(subject: String): Memo

    fun update(id: Int, subject: String): Memo?

    fun deleteById(id: Int): Long
}

class MemoService : IMemoService {
    override fun findById(userId: Int): Memo? {
        TODO("Not yet implemented")
    }

    override fun findAllSortedById(): List<Memo> {
        TODO("Not yet implemented")
    }

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
