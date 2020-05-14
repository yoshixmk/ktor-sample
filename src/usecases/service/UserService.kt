package yoshixmk.usecases.service

import org.koin.core.KoinComponent
import org.koin.core.inject
import yoshixmk.domains.irepository.IUserRepository
import yoshixmk.domains.users.User

interface IUserService : KoinComponent {
    fun findById(userId: Long): User
}

class UserService : IUserService {
    private val userRepository: IUserRepository by inject()
    override fun findById(userId: Long): User {
        return userRepository.findById(userId) ?: throw IllegalStateException("No User Found for Given Id")
    }
}
