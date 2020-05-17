package yoshixmk.usecases.service

import yoshixmk.domains.memos.Memo

class MockMemoService : IMemoService {
    private val data = listOf(
        Memo(1, "1のメモ"),
        Memo(2, "2のメモ")
    )

    override fun findById(id: Int): Memo? =
        data.find { m -> m.id == id }

    override fun findAllSortedById(): List<Memo> = data

    override fun create(subject: String): Memo =
        Memo(3, subject)

    override fun update(id: Int, subject: String): Memo? =
        if (id <= data.size)
            Memo(id, "更新しました")
        else null

    override fun deleteById(id: Int): Long =
        if (id <= data.size) 1 else 0
}
