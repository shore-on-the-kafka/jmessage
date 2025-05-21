package com.joon.jmessage.user.application.port.`in`.command

import com.joon.jmessage.user.domain.User

data class RegisterUserCommand(
    val userId: String,
    val oauthProvider: User.OauthProvider,
    val name: String
) {
    init {
        require(userId.isNotBlank()) { "email cannot be blank" }
        require(name.isNotBlank()) { "name cannot be blank" }
    }
}