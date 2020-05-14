package yoshixmk.interfaces.controllers

import org.koin.core.KoinComponent


interface IMemoController : KoinComponent {
    fun getUser(memoId: MemoId): Memo
    fun postUser(memo: MemoContent): Int
    fun putUser(memo: Memo): Memo
    fun deleteUser(memoId: MemoId): Unit
}

data class MemoId(val id: Int)

data class MemoContent(val subject: String)

data class Memo(val memo_id: Int, val subject: String)
