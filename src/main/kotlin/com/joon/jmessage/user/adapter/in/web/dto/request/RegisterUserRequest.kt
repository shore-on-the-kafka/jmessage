package com.joon.jmessage.user.adapter.`in`.web.dto.request

data class RegisterUserRequest(
    val name: String,
    val email: String,
    val password: String
)
