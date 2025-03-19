package com.joon.jmessage.message.application.port.`in`

import com.joon.jmessage.message.application.port.`in`.command.SendMessageCommand

interface SendMessageUseCase {
    fun sendMessage(sendMessageCommand: SendMessageCommand): String
}