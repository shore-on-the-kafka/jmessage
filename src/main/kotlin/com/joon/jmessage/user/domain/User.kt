package com.joon.jmessage.user.domain

import java.time.Instant

data class User(
    val userId: UserId,
    val name: String,
    val email: String,
    val password: String,
    val registerdAt: Instant
)