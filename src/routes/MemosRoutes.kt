package yoshixmk.routes

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import yoshixmk.json.Memo
import yoshixmk.json.MemoContent

fun Routing.memos() =
    route("memos") {
        get {
            val list = transaction {
                yoshixmk.databases.dao.Memo.all().sortedBy { it.id }
                    .map { m -> Memo(m.id.value, m.subject) }
            }
            call.respond(list)
        }

        post<MemoContent>("") { input ->
            call.respond(
                HttpStatusCode.Created,
                mapOf(
                    "memo_id" to transaction {
                        yoshixmk.databases.dao.Memo.new { this.subject = input.subject }
                    }.id.value
                )
            )
        }

        get("{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Invalid parameter: [${call.parameters["id"]}]"
            )
            val memoEntity =
                transaction {
                    yoshixmk.databases.dao.Memo.findById(id)
                } ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(
                Memo(
                    memoEntity.id.value,
                    memoEntity.subject
                )
            )
        }

        put("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.Companion.BadRequest)
            val memo = call.receive<yoshixmk.json.MemoContent>()
            val updatedCount = transaction {
                yoshixmk.databases.dao.Memos.update({ yoshixmk.databases.dao.Memos.id eq id }) {
                    it[subject] = memo.subject
                }
            }
            return@put if (updatedCount > 0)
                call.respond(
                    HttpStatusCode.Companion.OK,
                    Memo(id, memo.subject)
                )
            else
                call.respond(HttpStatusCode.Companion.NotFound)
        }

        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.Companion.BadRequest)
            val deletedCount =
                transaction { yoshixmk.databases.dao.Memos.deleteWhere { yoshixmk.databases.dao.Memos.id eq id } }
            return@delete if (deletedCount > 0)
                call.respond(HttpStatusCode.Companion.NoContent)
            else
                call.respond(HttpStatusCode.Companion.NotFound)
        }
    }
