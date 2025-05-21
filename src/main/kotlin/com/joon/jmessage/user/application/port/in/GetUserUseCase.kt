package com.joon.jmessage.user.application.port.`in`

import com.joon.jmessage.user.domain.User
import com.joon.jmessage.user.domain.UserKey

interface GetUserUseCase {
    fun getUserByUserKey(userKey: UserKey): User?
}