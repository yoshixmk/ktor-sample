package yoshixmk.json

// memo_idがスネークケースなのは、この命名がjsonのkeyに一致するため
data class Memo(val memo_id: Int, val subject: String)
