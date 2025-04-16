package com.joon.jmessage.user.adapter.`in`.web.dto.response

import com.joon.jmessage.user.domain.UserId

data class LoginUserResponse(
    val userId: UserId
) {

    companion object {
        fun of(userId: UserId): LoginUserResponse {
            return LoginUserResponse(userId)
        }
    }
}