package com.joon.jmessage.message.application

import com.joon.jmessage.message.application.exception.MessageException
import com.joon.jmessage.message.application.exception.UserException
import com.joon.jmessage.message.application.port.`in`.command.SendMessageCommand
import com.joon.jmessage.message.application.port.out.SaveMessageRepository
import com.joon.jmessage.user.application.port.`in`.MembershipCheckUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class SendMessageServiceTest {

    private val saveMessageRepository = mockk<SaveMessageRepository>()
    private val membershipCheckUseCase = mockk<MembershipCheckUseCase>()
    private val cut = SendMessageService(saveMessageRepository, membershipCheckUseCase)

    @Test
    fun `send dm success`() {
        // given
        val senderId = "senderId"
        val receiverId = "USER-01"
        val contents = "contents"
        val messageId = "messageId"
        val command = SendMessageCommand.of(userId = senderId, messageFrom = senderId, messageTo = receiverId, contents = contents)

        every { saveMessageRepository.saveDirectMessage(senderId, receiverId, contents) } returns messageId

        // when
        val result = cut.sendMessage(command)

        // then
        assertEquals(messageId, result)
        verify(exactly = 1) { saveMessageRepository.saveDirectMessage(senderId, receiverId, contents)}
        verify(exactly = 0) { saveMessageRepository.saveGroupMessage(any(), any(), any())}
    }

    @Test
    fun `send group message success`() {
        // given
        val senderId = "senderId"
        val receiverId = "GROUP-01"
        val contents = "contents"
        val messageId = "messageId"
        val command = SendMessageCommand.of(userId = senderId, messageFrom = senderId, messageTo = receiverId, contents = contents)

        every { saveMessageRepository.saveGroupMessage(senderId, receiverId, contents) } returns messageId

        // when
        val result = cut.sendMessage(command)

        // then
        assertEquals(messageId, result)
        verify(exactly = 0) { saveMessageRepository.saveDirectMessage(senderId, receiverId, contents)}
        verify(exactly = 1) { saveMessageRepository.saveGroupMessage(any(), any(), any())}
    }

    @Test
    fun `fail to send message when message type is invalid`() {
        // given
        val senderId = "senderId"
        val receiverId = "UNKNWON-01"
        val contents = "contents"
        val command = SendMessageCommand.of(userId = senderId, messageFrom = senderId, messageTo = receiverId, contents = contents)

        assertThrows<MessageException.InvalidMessageException> { cut.sendMessage(command) }
        verify(exactly = 0) { saveMessageRepository.saveDirectMessage(senderId, receiverId, contents)}
        verify(exactly = 0) { saveMessageRepository.saveGroupMessage(any(), any(), any())}
    }
}