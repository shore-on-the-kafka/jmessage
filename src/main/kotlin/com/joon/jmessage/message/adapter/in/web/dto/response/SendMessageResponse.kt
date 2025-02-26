package com.joon.jmessage.message.adapter.`in`.web.dto.response

data class SendMessageResponse(
    val messageId: String
) {
    companion object {
        fun of(messageId: String): SendMessageResponse {
            return SendMessageResponse(messageId)
        }
    }
}
