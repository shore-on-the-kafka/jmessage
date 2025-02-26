package com.joon.jmessage.message.domain

import java.time.Instant

class GroupMessage(
    messageId: String,
    contents: String,
    senderId: String,
    createdAt: Instant,
    val groupId: String,
    val readCount: Int = 0
) : Message(messageId, contents, senderId, createdAt) {

    override fun getMessageTo(): String {
        return groupId
    }
}