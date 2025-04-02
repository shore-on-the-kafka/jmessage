package com.joon.jmessage.user.application

import com.joon.jmessage.user.application.exception.UserException
import com.joon.jmessage.user.application.port.`in`.LoginUserUseCase
import com.joon.jmessage.user.application.port.`in`.command.LoginUserCommand
import com.joon.jmessage.user.application.port.out.GetUserRepository
import com.joon.jmessage.user.domain.UserId
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class LoginUserService(
    private val inMemoryUserRepository: GetUserRepository,
    private val passwordEncoder: PasswordEncoder
) : LoginUserUseCase {

    override fun login(loginUserCommand: LoginUserCommand): UserId {
        val email = loginUserCommand.email
        val password = loginUserCommand.password

        val user = inMemoryUserRepository.getUserByEmail(email) ?: throw UserException.NotFoundUserException()
        if (!passwordEncoder.matches(password, user.password)) {
            throw UserException.InvalidPasswordException()
        }
        return user.userId
    }
}