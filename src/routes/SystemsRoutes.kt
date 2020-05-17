package yoshixmk.routes

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.ContentType
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.websocket.webSocket

fun Routing.systems() {
    authenticate("basic-auth") {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }
        get("/echo/{echo}") {
            call.respondText(call.parameters["echo"] ?: "EMPTY", contentType = ContentType.Text.Plain)
        }

        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }
    }

    authenticate("jwt") {
        get("/boom") {
            // throw Exception("boom")
            call.respondText("boom!!")
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
