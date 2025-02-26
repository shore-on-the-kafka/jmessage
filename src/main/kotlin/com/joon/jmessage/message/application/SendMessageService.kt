package com.joon.jmessage.message.application

import com.joon.jmessage.message.application.exception.GroupException
import com.joon.jmessage.message.application.exception.MessageException
import com.joon.jmessage.message.application.port.`in`.SendMessageUseCase
import com.joon.jmessage.message.application.port.`in`.command.SendMessageCommand
import com.joon.jmessage.message.application.port.out.SaveMessageRepository
import com.joon.jmessage.user.application.port.`in`.MembershipCheckUseCase
import org.springframework.stereotype.Service

@Service
class SendMessageService(
    private val inMemoryMessageRepository: SaveMessageRepository,
    private val membershipCheckService: MembershipCheckUseCase
) : SendMessageUseCase {
    override fun sendMessage(sendMessageCommand: SendMessageCommand): String {
        val messageTo = sendMessageCommand.messageTo
        val messageFrom = sendMessageCommand.messageFrom
        val contents = sendMessageCommand.contents

        return if (sendMessageCommand.isDirectMessage()) {
            sendDirectMessage(messageFrom, messageTo, contents)
        } else if (sendMessageCommand.isGroupMessage()) {
            sendGroupMessage(messageTo, messageFrom, contents)
        } else {
            throw MessageException.InvalidMessageException()
        }
    }

    private fun sendGroupMessage(messageTo: String, messageFrom: String, contents: String): String {
        val isJoined = membershipCheckService.isJoinedMembership(
            groupId = messageTo,
            userId = messageFrom
        )
        if (!isJoined) {
            throw GroupException.UnauthorizedException()
        }

        return inMemoryMessageRepository.saveGroupMessage(
            senderId = messageFrom,
            groupId = messageTo,
            contents = contents
        )
    }

    private fun sendDirectMessage(messageFrom: String, messageTo: String, contents: String) =
        inMemoryMessageRepository.saveDirectMessage(
            senderId = messageFrom,
            receiverId = messageTo,
            contents = contents
        )
}