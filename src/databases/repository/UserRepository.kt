package yoshixmk.databases.repository

import yoshixmk.domains.irepository.IUserRepository
import yoshixmk.domains.users.User

class UserRepository : IUserRepository {
    override fun findById(userId: Long): User? {
        // TODO DBからのデータ形式を、domainsに詰め替え。Service層にinject
        return User(1, "Test", "Taro")
    }
}
