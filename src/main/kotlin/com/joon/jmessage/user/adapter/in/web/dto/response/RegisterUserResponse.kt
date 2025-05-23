package com.joon.jmessage.user.adapter.`in`.web.dto.response

import com.joon.jmessage.user.domain.UserId

data class RegisterUserResponse(
    val userId: UserId,
    val accessToken: String
) {

    companion object {
        fun of(userId: UserId, accessToken: String): RegisterUserResponse {
            return RegisterUserResponse(userId, accessToken)
        }
    }
}
