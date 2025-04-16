package com.joon.jmessage.user.application

import com.joon.jmessage.user.application.port.`in`.command.RegisterUserCommand
import com.joon.jmessage.user.application.port.out.RegisterUserRepository
import com.joon.jmessage.user.domain.UserId
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.test.assertNotNull

class RegisterUserServiceTest {

    private val inMemoryUserRepository = mockk<RegisterUserRepository>()
    private val passwordEncoder = mockk<PasswordEncoder>()

    private val cut = RegisterUserService(inMemoryUserRepository, passwordEncoder)

    @Test
    fun `success to register user`() {
        // given
        val userId = UserId("userId")
        val name = "dummyUser"
        val email = "dummy@email.com"
        val password = "password"
        val encodedPassword = "encodedPassword"
        val command = RegisterUserCommand(name, email, password)

        every { passwordEncoder.encode(password) } returns encodedPassword
        every { inMemoryUserRepository.registerUser(name, email, encodedPassword) } returns userId

        // when
        val result = cut.registerUser(command)

        // then
        assertNotNull(result)
        assert(result == userId)
        verify(exactly = 1) { passwordEncoder.encode(password) }
        verify(exactly = 1) { inMemoryUserRepository.registerUser(name, email, encodedPassword) }
    }

}