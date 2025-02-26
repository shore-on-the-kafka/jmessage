package com.joon.jmessage.message.application.port.`in`.command

data class ReadGroupMessagesCommand(
    val groupId: String,
    val userId: String
)
