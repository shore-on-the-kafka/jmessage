package com.joon.jmessage.message.adapter.`in`.web.dto.response

import com.joon.jmessage.message.domain.Message
import java.time.Instant

data class MessageViewResponse(
    val messageId: String,
    val messageFrom: String,
    val messageTo: String,
    val contents: String,
    val createdAt: Instant
) {
    companion object {
        fun of(message: Message): MessageViewResponse {
            return MessageViewResponse(
                messageId = message.messageId,
                messageFrom = message.senderId,
                messageTo = message.getMessageTo(),
                contents = message.contents,
                createdAt = message.createdAt
            )
        }
    }
}
