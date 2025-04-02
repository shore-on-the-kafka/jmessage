package com.joon.jmessage.user.application

import com.joon.jmessage.message.application.exception.MessageException
import com.joon.jmessage.user.application.exception.UserException
import com.joon.jmessage.user.application.port.`in`.command.LoginUserCommand
import com.joon.jmessage.user.application.port.out.GetUserRepository
import com.joon.jmessage.user.domain.User
import com.joon.jmessage.user.domain.UserId
import io.mockk.every
import io.mockk.mockk
import jakarta.annotation.PostConstruct
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.Instant
import kotlin.test.assertNotNull

class LoginUserServiceTest {

    private val inMemoryUserRepository = mockk<GetUserRepository>()
    private val passwordEncoder = mockk<PasswordEncoder>()

    private val cut = LoginUserService(inMemoryUserRepository, passwordEncoder)

    @Test
    fun `success to login`() {
        // given
        val email = "dummy@email.com"
        val password = "password"

        val user = User(UserId("userId"), "dummyUser", email, password, Instant.now())
        val command = LoginUserCommand(email, password)

        every { inMemoryUserRepository.getUserByEmail(email) } returns user
        every { passwordEncoder.matches(password, user.password) } returns true

        // when
        val result = cut.login(command)

        // then
        assertNotNull(result)
        assert(result == user.userId)
    }

    @Test
    fun `not registered user login`() {
        // given
        val email = "dummy@email.com"
        val password = "password"

        val user = User(UserId("userId"), "dummyUser", email, password, Instant.now())
        val command = LoginUserCommand(email, password)

        every { inMemoryUserRepository.getUserByEmail(email) } returns null

        // when, then
        assertThrows<UserException.NotFoundUserException> { cut.login(command) }
    }

    @Test
    fun `fail to login when password is incorrect`() {
        // given
        val email = "dummy@email.com"
        val password = "password"

        val user = User(UserId("userId"), "dummyUser", email, password, Instant.now())
        val command = LoginUserCommand(email, password)

        every { inMemoryUserRepository.getUserByEmail(email) } returns user
        every { passwordEncoder.matches(password, user.password) } returns false

        // when, then
        assertThrows<UserException.InvalidPasswordException> { cut.login(command) }
    }
}