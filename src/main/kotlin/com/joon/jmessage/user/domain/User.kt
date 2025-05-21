package com.joon.jmessage.user.domain

import java.time.Instant

data class User(
    val userKey: UserKey,
    val name: String,
    val registeredAt: Instant
) {
    enum class OauthProvider {
        LINE,
        GOOGLE
    }
}