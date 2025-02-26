package com.joon.jmessage.message.application.command

import com.joon.jmessage.message.application.exception.UserException
import com.joon.jmessage.message.application.port.`in`.command.SendMessageCommand
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SendMessageCommandTest {

    @Test
    fun `throw exception when request userId is not equal to messageFrom`() {
        // given
        val senderId = "senderId"
        val userId = "not-senderId"
        val receiverId = "USER-01"
        val contents = "contents"

        assertThrows<UserException.UnauthorizedException> {
            SendMessageCommand.of(userId = userId,
                messageFrom = senderId,
                messageTo = receiverId,
                contents = contents
            )
        }
    }
}