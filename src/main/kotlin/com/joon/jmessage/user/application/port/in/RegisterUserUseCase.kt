package com.joon.jmessage.user.application.port.`in`

import com.joon.jmessage.user.application.port.`in`.command.RegisterUserCommand
import com.joon.jmessage.user.domain.UserId

interface RegisterUserUseCase {
    fun registerUser(registerUserCommand: RegisterUserCommand): UserId
}