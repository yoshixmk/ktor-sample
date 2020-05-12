package yoshixmk.jwt.sample

import io.ktor.auth.Principal

data class User(
    val id: Int,
    val name: String,
    val countries: List<String>
) : Principal {
    companion object {
        val testUser = User(1, "Test", listOf("Egypt", "Austria"))
    }

    // fun findUserById(id: Int): User = testUser
}
