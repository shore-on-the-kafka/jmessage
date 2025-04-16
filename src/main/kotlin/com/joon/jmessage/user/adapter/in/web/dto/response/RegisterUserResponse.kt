package com.joon.jmessage.user.adapter.`in`.web.dto.response

import com.joon.jmessage.user.domain.UserId

data class RegisterUserResponse(
    val userId: UserId
) {

    companion object {
        fun of(userId: UserId): RegisterUserResponse {
            return RegisterUserResponse(userId)
        }
    }
}
