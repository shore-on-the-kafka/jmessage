package com.joon.jmessage.user.adapter.out.persistence

import com.joon.jmessage.user.adapter.out.persistence.idgenerator.UserIdGenerator
import com.joon.jmessage.user.application.port.out.GetUserRepository
import com.joon.jmessage.user.application.port.out.RegisterUserRepository
import com.joon.jmessage.user.domain.User
import com.joon.jmessage.user.domain.UserId
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class InMemoryUserRepository(
    private val userIdGenerator: UserIdGenerator
) : RegisterUserRepository,
    GetUserRepository
{
    private val users = mutableMapOf<UserId, User>()

    override fun registerUser(name: String, email: String, password: String): UserId {
        val userId = userIdGenerator.getNewUserId()
        val user = User(
            userId = userId,
            name = name,
            email = email,
            password = password,
            registerdAt = Instant.now()
        )
        users[userId] = user
        return userId
    }

    override fun getUserById(userId: UserId): User? {
        return users[userId]
    }

    override fun getUserByEmail(email: String): User? {
        return users.values.find { it.email == email }
    }
}