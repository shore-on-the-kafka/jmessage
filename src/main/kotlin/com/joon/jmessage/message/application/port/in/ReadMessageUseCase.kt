package com.joon.jmessage.message.application.port.`in`

import com.joon.jmessage.message.application.port.`in`.command.ReadAllMessagesCommand
import com.joon.jmessage.message.application.port.`in`.command.ReadGroupMessagesCommand
import com.joon.jmessage.message.domain.GroupMessage
import com.joon.jmessage.message.domain.Message

interface ReadMessageUseCase {
    fun findSendedAllMessagesByUserId(senderId: String): List<Message>
    fun findReceivedGroupMessagesByUserIdAndGroupId(readGroupMessagesCommand: ReadGroupMessagesCommand): List<GroupMessage>
    fun findReceivedAllMessagesByUserId(readAllMessagesCommand: ReadAllMessagesCommand): List<Message>
}