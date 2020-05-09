package yoshixmk

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.basic
import io.ktor.auth.jwt.jwt
import io.ktor.features.*
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.cio.websocket.pingPeriod
import io.ktor.http.cio.websocket.timeout
import io.ktor.jackson.jackson
import io.ktor.request.path
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import jwt.sample.JwtConfig
import jwt.sample.User
import org.jetbrains.exposed.sql.Database
import org.slf4j.event.Level
import routes.routes
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

    install(Authentication) {
        basic(name = "basic-auth") {
            realm = "Ktor Server"
            validate { credentials ->
                if (credentials.name == name && credentials.password == name) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }

        jwt(name = "jwt") {
            verifier(JwtConfig.verifier)
            realm = "ktor.io"
            validate {
                if (it.payload.claims.contains("id")) User.testUser else null
            }
        }
    }

    routing {
        routes()

        post("/login") {
            // val credentials = call.receive<UserPasswordCredential>()
            val user = User.testUser // user by credentials
            val token = JwtConfig.makeToken(user)
            call.respondText(token)
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
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
