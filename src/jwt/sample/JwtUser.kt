package yoshixmk.jwt.sample

import io.ktor.auth.Principal

data class JwtUser(
    val id: Int,
    val name: String,
    val countries: List<String>
) : Principal {
    companion object {
        val testUser = JwtUser(1, "Test", listOf("Egypt", "Austria"))
    }

    // fun findUserById(id: Int): User = testUser
}
