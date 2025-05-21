package com.joon.jmessage.user.domain

import com.joon.jmessage.user.domain.User.*

data class UserKey private constructor(val value: String) {
    init {
        require(value.isNotBlank()) { "User id must not be blank" }
    }

    companion object {
        fun of(userId: String, oauthProvider: OauthProvider): UserKey {
            return UserKey("${oauthProvider.name}-$userId")
        }
    }
}
