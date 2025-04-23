package com.joon.jmessage.user.adapter.`in`.web.dto.response

import com.joon.jmessage.user.domain.UserId

data class LoginUserResponse(
    val userId: UserId,
    val accessToken: String
) {

    companion object {
        fun of(userId: UserId, accessToken: String): LoginUserResponse {
            return LoginUserResponse(userId, accessToken)
        }
    }
}