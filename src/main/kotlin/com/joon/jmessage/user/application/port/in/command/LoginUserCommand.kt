package com.joon.jmessage.user.application.port.`in`.command

data class LoginUserCommand(
    val email: String,
    val password: String
)
