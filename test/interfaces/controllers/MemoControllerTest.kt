package yoshixmk.interfaces.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import yoshixmk.module
import yoshixmk.testKoinModules
import kotlin.test.Test
import kotlin.test.assertEquals

@KtorExperimentalAPI
class MemoControllerTest : AutoCloseKoinTest() {

    init {
        startKoin { modules(testKoinModules) }
    }

    private val jacksonObjectMapper = jacksonObjectMapper()

    @Test
    fun GET_memos_200が返ってくること() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/memos").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val actualSize = jacksonObjectMapper.readTree(response.content).size()
                assertEquals(2, actualSize)
            }
        }
    }

    @Test
    fun GET_memosId_200が返ってくること() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/memos/1").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun POST_memos_201が返ってくること() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/memos") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("""{"subject": "これはメモです"}""")
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
            }
        }
    }

    @Test
    fun PUT_memos_200が返ってくること() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Put, "/memos/1") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("""{"subject": "1のメモを更新"}""")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun DELETE_memosId_204が返ってくること() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Delete, "/memos/1").apply {
                assertEquals(HttpStatusCode.NoContent, response.status())
            }
        }
    }
}
