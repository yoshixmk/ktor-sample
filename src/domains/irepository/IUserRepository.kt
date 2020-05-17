package yoshixmk.domains.irepository

import yoshixmk.domains.users.User

interface IUserRepository {
    fun findById(userId: Long): User?
}
