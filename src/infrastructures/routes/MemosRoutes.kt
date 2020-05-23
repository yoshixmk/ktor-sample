package yoshixmk.infrastructures.routes

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.inject
import yoshixmk.interfaces.controllers.IMemoController
import yoshixmk.interfaces.controllers.Memo
import yoshixmk.interfaces.controllers.MemoContent
import yoshixmk.interfaces.controllers.MemoId

fun Routing.memos() {

    val memoController: IMemoController by inject()

    route("memos") {
        get {
            val list = memoController.getMemos()
            call.respond(list)
        }

        post<MemoContent>("") { input ->
            call.respond(
                HttpStatusCode.Created,
                memoController.postMemo(MemoContent(input.subject))
            )
        }

        get("{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Invalid parameter: [${call.parameters["id"]}]"
            )
            val memoEntity = memoController.getMemo(MemoId(id))
                ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(memoEntity)
        }

        put("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.Companion.BadRequest)
            val memo = call.receive<MemoContent>()
            val createdMemo = memoController.putMemo(Memo(id, memo.subject))
            return@put if (createdMemo != null)
                call.respond(
                    HttpStatusCode.Companion.OK,
                    createdMemo
                )
            else
                call.respond(HttpStatusCode.Companion.NotFound)
        }

        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.Companion.BadRequest)
            val deletedMemo = memoController.deleteMemo(MemoId(id))
            return@delete if (deletedMemo != null)
                call.respond(HttpStatusCode.Companion.NoContent)
            else
                call.respond(HttpStatusCode.Companion.NotFound)
        }
    }
}
