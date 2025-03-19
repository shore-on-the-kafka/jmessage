package com.joon.jmessage.message.domain

import java.time.Instant

abstract class Message(
    val messageId: String,
    val contents: String,
    val senderId: String,
    val createdAt: Instant
) {
    abstract fun getMessageTo(): String
}