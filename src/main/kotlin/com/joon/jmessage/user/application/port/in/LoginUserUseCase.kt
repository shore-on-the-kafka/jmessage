package com.joon.jmessage.user.application.port.`in`

import com.joon.jmessage.user.application.port.`in`.command.LoginUserCommand
import com.joon.jmessage.user.domain.UserId

interface LoginUserUseCase {
    fun login(loginUserCommand: LoginUserCommand): UserId
}