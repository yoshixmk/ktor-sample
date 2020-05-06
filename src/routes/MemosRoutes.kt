package routes

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import json.Memo
import json.MemoContent
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

fun Routing.memos() =
    route("memos") {
        get("") {
            val list = transaction {
                infrastructure.dao.Memo.all().sortedBy { it.id }
                    .map { m -> Memo(m.id.value, m.subject) }
            }
            call.respond(list)
        }

        post<MemoContent>("") { input ->
            call.respond(
                HttpStatusCode.Created,
                mapOf(
                    "memo_id" to transaction {
                        infrastructure.dao.Memo.new {
                            this.subject = input.subject
                        }
                    }.id.value
                )
            )
        }

        get("{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@get call.respond(HttpStatusCode.BadRequest)
            val memoEntity =
                transaction {
                    infrastructure.dao.Memo.findById(
                        id
                    )
                }
                    ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(Memo(memoEntity.id.value, memoEntity.subject))
        }

        put("{id}") {
            val id = call.parameters["id"]?.toInt()
                ?: return@put call.respond(io.ktor.http.HttpStatusCode.Companion.BadRequest)
            val memo = call.receive<json.MemoContent>()
            val updatedCount = org.jetbrains.exposed.sql.transactions.transaction {
                infrastructure.dao.Memos.update({ infrastructure.dao.Memos.id eq id }) {
                    it[infrastructure.dao.Memos.subject] = memo.subject
                }
            }
            return@put if (updatedCount > 0)
                call.respond(
                    io.ktor.http.HttpStatusCode.Companion.OK,
                    json.Memo(id, memo.subject)
                )
            else
                call.respond(io.ktor.http.HttpStatusCode.Companion.NotFound)
        }

        delete("{id}") {
            val id = call.parameters["id"]?.toInt()
                ?: return@delete call.respond(io.ktor.http.HttpStatusCode.Companion.BadRequest)
            val deletedCount =
                org.jetbrains.exposed.sql.transactions.transaction { infrastructure.dao.Memos.deleteWhere { infrastructure.dao.Memos.id eq id } }
            return@delete if (deletedCount > 0)
                call.respond(io.ktor.http.HttpStatusCode.Companion.NoContent)
            else
                call.respond(io.ktor.http.HttpStatusCode.Companion.NotFound)
        }
    }