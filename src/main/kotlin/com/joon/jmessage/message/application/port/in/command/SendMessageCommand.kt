package com.joon.jmessage.message.application.port.`in`.command

import com.joon.jmessage.message.application.exception.UserException

data class SendMessageCommand private constructor(
    val messageFrom: String,
    val messageTo: String,
    val contents: String
) {
    init {
        require(messageFrom.isNotBlank()) { "messageFrom cannot be blank" }
        require(messageTo.isNotBlank()) { "messageTo cannot be blank" }
        require(contents.isNotBlank()) { "contents cannot be blank" }
    }

    companion object {
        fun of(userId: String, messageFrom: String, messageTo: String, contents: String): SendMessageCommand {
            if (userId != messageFrom) {
                throw UserException.UnauthorizedException()
            }
            return SendMessageCommand(messageFrom, messageTo, contents)
        }
    }

    fun isDirectMessage(): Boolean {
        return messageTo.startsWith("USER")
    }

    fun isGroupMessage(): Boolean {
        return messageTo.startsWith("GROUP")
    }
}
