package yoshixmk.interfaces.controllers

import yoshixmk.domains.users.User
import yoshixmk.usecases.service.IUserService

interface IUserController {

    fun getUser(userId: UserId): UserResponse
}

class UserController(private val userService: IUserService) : IUserController {
    override fun getUser(userId: UserId): UserResponse {
        return userService.findById(userId.id).toResponse()
    }
}

// IN
data class UserId(var id: Long)

// OUT
data class UserResponse(var userId: Long, var familyName: String, var givenName: String)

private fun User.toResponse() = UserResponse(id, familyName, givenName)
