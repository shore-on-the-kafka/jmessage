package com.joon.jmessage.user.application.port.out

import com.joon.jmessage.user.domain.UserId

interface RegisterUserRepository {

    fun registerUser(name: String, email: String, password: String): UserId
}