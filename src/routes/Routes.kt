package yoshixmk.routes

import io.ktor.http.content.resource
import io.ktor.http.content.static
import io.ktor.routing.Routing
import yoshixmk.infrastructures.routes.users

fun Routing.routes() {
    // システム全般、サンプルコード
    systems()

    // メモ機能
    memos()

    // ユーザ機能
    users()

    // 静的アクセス
    static("/") {
        resource("favicon.ico")
    }
}
