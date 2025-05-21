package com.joon.jmessage.user.adapter.out.persistence

import com.joon.jmessage.user.application.port.out.GetUserRepository
import com.joon.jmessage.user.application.port.out.RegisterUserRepository
import com.joon.jmessage.user.domain.User
import com.joon.jmessage.user.domain.UserKey
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class InMemoryUserRepository(
) : RegisterUserRepository,
    GetUserRepository
{
    private val users = mutableMapOf<UserKey, User>()

    @PostConstruct
    fun setUp() {
        val userKey = UserKey.of("dummyId", User.OauthProvider.LINE)
        users[userKey] = User(userKey, "dummy", Instant.now())
    }

    override fun registerUser(userKey: UserKey, name: String): User {
        val user = User(
            userKey = userKey,
            name = name,
            registeredAt = Instant.now()
        )
        users[userKey] = user
        return user
    }

    override fun getUserByUserKey(userKey: UserKey): User? {
        return users[userKey]
    }
}