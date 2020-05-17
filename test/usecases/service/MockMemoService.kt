package yoshixmk.usecases.service

import yoshixmk.domains.memos.Memo

class MockMemoService : IMemoService {
    override fun findById(userId: Int): Memo? {
        TODO("Not yet implemented")
    }

    override fun findAllSortedById() =
        listOf(
            Memo(1, "1のメモ"),
            Memo(2, "2のメモ")
        )

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
