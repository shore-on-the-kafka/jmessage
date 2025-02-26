package com.joon.jmessage.message.domain

import java.time.Instant

class DirectMessage(
    messageId: String,
    contents: String,
    senderId: String,
    createdAt: Instant,
    val receiverId: String,
    val hasBeenRead: Boolean = false
) : Message(messageId, contents, senderId, createdAt) {

    override fun getMessageTo(): String {
        return receiverId
    }
}