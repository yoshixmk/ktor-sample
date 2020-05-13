package yoshixmk.infrastructures.routes

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import yoshixmk.databases.repository.UserRepository
import yoshixmk.interfaces.controllers.UserController
import yoshixmk.interfaces.controllers.UserId
import yoshixmk.usecases.service.UserService

fun Routing.users() {

    // TODO injectを用いる
    val userController = UserController(UserService(UserRepository()))

    route("v1/users") {
        route("{id}") {
            get {
                val userId = call.parameters["id"]?.toLongOrNull() ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "Invalid parameter: [${call.parameters["id"]}]"
                )
                call.respond(userController.getUser(UserId(userId)))
            }
        }
    }
}
