package com.joon.jmessage.user.application.port.out

import com.joon.jmessage.user.domain.User
import com.joon.jmessage.user.domain.UserKey

interface RegisterUserRepository {

    fun registerUser(userKey: UserKey, name: String): User
}