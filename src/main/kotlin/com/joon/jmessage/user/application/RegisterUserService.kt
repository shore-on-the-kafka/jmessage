package com.joon.jmessage.user.application

import com.joon.jmessage.user.application.port.`in`.RegisterUserUseCase
import com.joon.jmessage.user.application.port.`in`.command.RegisterUserCommand
import com.joon.jmessage.user.application.port.out.RegisterUserRepository
import com.joon.jmessage.user.domain.UserId
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class RegisterUserService(
    private val inMemoryRegisterUserRepository: RegisterUserRepository,
    private val passwordEncoder: PasswordEncoder
) : RegisterUserUseCase {

    override fun registerUser(registerUser: RegisterUserCommand): UserId {
        val name = registerUser.name
        val email = registerUser.email
        val password = passwordEncoder.encode(registerUser.password)

        return inMemoryRegisterUserRepository.registerUser(name, email, password)
    }
}