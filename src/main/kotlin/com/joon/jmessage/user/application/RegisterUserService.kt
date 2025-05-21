package com.joon.jmessage.user.application

import com.joon.jmessage.user.adapter.out.persistence.InMemoryUserRepository
import com.joon.jmessage.user.application.port.`in`.RegisterUserUseCase
import com.joon.jmessage.user.application.port.`in`.command.RegisterUserCommand
import com.joon.jmessage.user.application.port.out.RegisterUserRepository
import com.joon.jmessage.user.domain.User
import com.joon.jmessage.user.domain.UserKey
import org.springframework.stereotype.Service

@Service
class RegisterUserService(
    private val inMemoryUserRepository: RegisterUserRepository,
) : RegisterUserUseCase {

    override fun registerUser(command: RegisterUserCommand): User {
        val userId = command.userId
        val oauthProvider = command.oauthProvider
        val name = command.name

        return inMemoryUserRepository.registerUser(UserKey.of(userId, oauthProvider), name)
    }
}