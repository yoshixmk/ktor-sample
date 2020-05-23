package yoshixmk.interfaces.controllers

import yoshixmk.usecases.service.IMemoService

interface IMemoController {
    fun getMemo(memoId: MemoId): Memo?
    fun getMemos(): List<Memo>
    fun postMemo(memo: MemoContent): Memo
    fun putMemo(memo: Memo): Memo?
    fun deleteMemo(memoId: MemoId): Memo?
}

class MemoController(private val service: IMemoService) : IMemoController {
    override fun getMemo(memoId: MemoId): Memo? =
        service.findById(memoId.id)?.let { memo -> Memo(memo.id, memo.subject) }

    override fun getMemos(): List<Memo> =
        service.findAllSortedById().map { m -> Memo(m.id, m.subject) }

    override fun postMemo(memo: MemoContent): Memo =
        service.create(memo.subject).let { m -> Memo(m.id, m.subject) }

    override fun putMemo(memo: Memo): Memo? =
        service.update(memo.memo_id, memo.subject)?.let { m -> Memo(m.id, m.subject) }

    override fun deleteMemo(memoId: MemoId): Memo? =
        service.deleteById(memoId.id)?.let { m -> Memo(m.id, m.subject) }
}

data class MemoId(val id: Int)

data class MemoContent(val subject: String)

data class Memo(val memo_id: Int, val subject: String)
