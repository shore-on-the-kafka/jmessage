package com.joon.jmessage.user.application.port.`in`.command

import com.joon.jmessage.message.application.exception.UserException
import com.joon.jmessage.message.application.port.`in`.command.SendMessageCommand

data class RegisterUserCommand(
    val name: String,
    val email: String,
    val password: String
) {
    init {
        require(name.isNotBlank()) { "name cannot be blank" }
        require(email.isNotBlank()) { "email cannot be blank" }
        require(password.isNotBlank()) { "password cannot be blank" }
    }
}