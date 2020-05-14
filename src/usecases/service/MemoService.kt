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
