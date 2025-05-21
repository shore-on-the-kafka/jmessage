package com.joon.jmessage.user.application

import com.joon.jmessage.user.application.port.`in`.command.RegisterUserCommand
import com.joon.jmessage.user.application.port.out.RegisterUserRepository
import com.joon.jmessage.user.domain.User
import com.joon.jmessage.user.domain.User.OauthProvider
import com.joon.jmessage.user.domain.UserKey
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.Instant
import kotlin.test.assertNotNull

class RegisterUserServiceTest {

    private val inMemoryUserRepository = mockk<RegisterUserRepository>()

    private val cut = RegisterUserService(inMemoryUserRepository)

    @Test
    fun `success to register user`() {
        // given
        val userId = "userId"
        val oauthProvider = OauthProvider.LINE
        val userKey = UserKey.of(userId, oauthProvider)

        val name = "dummyUser"
        val command = RegisterUserCommand(userId, oauthProvider, name)
        val user = User(userKey, name, Instant.now())

        every { inMemoryUserRepository.registerUser(userKey, name) } returns user

        // when
        val result = cut.registerUser(command)

        // then
        assertNotNull(result)
        assert(result == user)
        verify(exactly = 1) { inMemoryUserRepository.registerUser(userKey, name) }
    }

}