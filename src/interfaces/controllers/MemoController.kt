package yoshixmk.interfaces.controllers

import yoshixmk.usecases.service.IMemoService


interface IMemoController {
    fun getMemo(memoId: MemoId): Memo
    fun getMemos(): List<Memo>
    fun postMemo(memo: MemoContent): Int
    fun putMemo(memo: Memo): Memo
    fun deleteMemo(memoId: MemoId)
}

class MemoController(private val service: IMemoService) : IMemoController {
    override fun getMemo(memoId: MemoId): Memo {
        TODO("Not yet implemented")
    }

    override fun getMemos(): List<Memo> =
        service.findAllSortedById().map { m -> Memo(m.id!!, m.subject) }

    override fun postMemo(memo: MemoContent): Int {
        TODO("Not yet implemented")
    }

    override fun putMemo(memo: Memo): Memo {
        TODO("Not yet implemented")
    }

    override fun deleteMemo(memoId: MemoId) {
        TODO("Not yet implemented")
    }
}

data class MemoId(val id: Int)

data class MemoContent(val subject: String)

data class Memo(val memo_id: Int, val subject: String)
