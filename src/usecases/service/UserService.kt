package yoshixmk.usecases.service

import yoshixmk.domains.irepository.IUserRepository
import yoshixmk.domains.users.User

interface IUserService {
    fun findById(userId: Long): User
}

class UserService(
    private val userRepository: IUserRepository
) :
    IUserService {
    override fun findById(userId: Long): User {
        return userRepository.findById(userId) ?: throw IllegalStateException("No User Found for Given Id")
    }
}
