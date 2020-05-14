package yoshixmk.infrastructures.routes

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import org.koin.ktor.ext.inject
import yoshixmk.interfaces.controllers.IUserController
import yoshixmk.interfaces.controllers.UserId

fun Routing.users() {

    val userController: IUserController by inject()

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
