package yoshixmk

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @KtorExperimentalAPI
    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/") {
                addHeader(
                    "Authorization",
                    "Basic YmFzaWMtYXV0aDpiYXNpYy1hdXRo"
                )
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }
}
