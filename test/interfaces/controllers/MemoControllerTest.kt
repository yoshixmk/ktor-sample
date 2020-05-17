package interfaces.controllers

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import org.koin.core.context.startKoin
import yoshixmk.module
import yoshixmk.testKoinModules
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MemoControllerTest {

    @KtorExperimentalAPI
    @Test
    fun GET_memos_200が返ってくること() {
        withTestApplication({ module(testing = true) }) {
            startKoin { modules(testKoinModules) }
            handleRequest(HttpMethod.Get, "/memos").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                // assertEquals("HELLO WORLD!", response.content)
            }
        }
    }

    fun postUser() {
    }

    fun putUser() {
    }

    fun deleteUser() {
    }
}