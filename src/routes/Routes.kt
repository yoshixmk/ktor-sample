package yoshixmk.routes

import io.ktor.http.content.resource
import io.ktor.http.content.static
import io.ktor.routing.Routing

fun Routing.routes() {
    // システム全般、サンプルコード
    systems()

    // メモ機能
    memos()

    // 静的アクセス
    static("/") {
        resource("favicon.ico")
    }
}
