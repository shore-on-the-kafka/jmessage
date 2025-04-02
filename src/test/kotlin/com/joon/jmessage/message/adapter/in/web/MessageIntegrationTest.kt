package com.joon.jmessage.message.adapter.`in`.web

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MessageIntegrationTest @Autowired constructor(
    private val mockMvc: MockMvc
) {
    private val DUMMY_USER_ID = "USER-dummy"
    private val DUMMY_USER_PASSWORD = "dummy"

    @Test
    fun `unauthorized request should return 401 Unauthorized`() {
        mockMvc.get("/v1/messages")
            .andExpect {
                status { isUnauthorized() } // 401 Unauthorized
            }
    }

    @Test
    fun `unregistered user request should return 401 Unauthorized`() {
        mockMvc.get("/v1/messages") {
            with(httpBasic("unregistered", "password"))
        }.andExpect {
            status { isUnauthorized() } // 200 OK
        }
    }

    @Test
    fun `authorized request should return 200 OK`() {
        mockMvc.get("/v1/messages") {
            header("X-User-Id", DUMMY_USER_ID)
            with(httpBasic(DUMMY_USER_ID, DUMMY_USER_PASSWORD))
        }.andExpect {
            status { isOk() } // 200 OK
        }
    }
}