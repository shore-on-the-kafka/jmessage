package com.joon.jmessage.user.application.port.out

import com.joon.jmessage.user.domain.User
import com.joon.jmessage.user.domain.UserId

interface GetUserRepository {

    fun getUserById(userId: UserId): User?
    fun getUserByEmail(email: String): User?
}