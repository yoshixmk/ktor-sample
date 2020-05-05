package yoshixmk

import com.fasterxml.jackson.databind.SerializationFeature
import infrastructure.dao.Memo
import infrastructure.dao.Memos
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.*
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.pingPeriod
import io.ktor.http.cio.websocket.readText
import io.ktor.http.cio.websocket.timeout
import io.ktor.http.content.resource
import io.ktor.http.content.static
import io.ktor.jackson.jackson
import io.ktor.request.path
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import io.ktor.websocket.webSocket
import json.MemoContent
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.slf4j.event.Level
import java.time.Duration

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
//@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(@Suppress("UNUSED_PARAMETER") testing: Boolean = false) {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    install(AutoHeadResponse)

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(ConditionalHeaders)

    install(io.ktor.websocket.WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.     
    }

    if (!testing) {
        val config = environment.config
        Database.connect(
            url = config.property("database.url").getString(),
            user = environment.config.property("database.user").getString(),
            password = environment.config.property("database.password").getString(),
            driver = "org.postgresql.Driver"
        )
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/echo/{echo}") {
            call.respondText(call.parameters["echo"] ?: "EMPTY", contentType = ContentType.Text.Plain)
        }

        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }

        get("/boom") {
            throw Exception("boom")
        }

        get("/memos") {
            val list = transaction {
                Memo.all().sortedBy { it.id }.map { m -> json.Memo(m.id.value, m.subject) }
            }
            call.respond(list)
        }

        post<MemoContent>("/memos") { input ->
            call.respond(
                HttpStatusCode.Created,
                mapOf(
                    "memo_id" to transaction {
                        Memo.new {
                            this.subject = input.subject
                        }
                    }.id.value
                )
            )
        }

        get("/memos/{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@get call.respond(HttpStatusCode.BadRequest)
            val memoEntity =
                transaction { Memo.findById(id) }
                    ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(json.Memo(memoEntity.id.value, memoEntity.subject))
        }

        put("/memos/{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val memo = call.receive<MemoContent>()
            val updatedCount = transaction {
                Memos.update({ Memos.id eq id }) {
                    it[subject] = memo.subject
                }
            }
            return@put if (updatedCount > 0)
                call.respond(HttpStatusCode.OK, json.Memo(id, memo.subject))
            else
                call.respond(HttpStatusCode.NotFound)
        }

        static("/") {
            resource("favicon.ico")
        }

        install(StatusPages) {
            exception<AuthenticationException> { e ->
                log.info(e.stackTrace.toString())
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> { e ->
                log.info(e.stackTrace.toString())
                call.respond(HttpStatusCode.Forbidden)
            }
            exception<Exception> { e ->
                log.error(e.message)
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        webSocket("/myws/echo") {
            send(Frame.Text("Hi from server"))
            while (true) {
                val frame = incoming.receive()
                if (frame is Frame.Text) {
                    send(Frame.Text("Client said: " + frame.readText()))
                }
            }
        }
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

