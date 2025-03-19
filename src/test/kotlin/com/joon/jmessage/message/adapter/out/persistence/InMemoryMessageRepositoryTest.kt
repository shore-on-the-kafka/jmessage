package com.joon.jmessage.message.adapter.out.persistence

import com.joon.jmessage.message.adapter.out.persistence.idgenerator.MessageIdGenerator
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class InMemoryMessageRepositoryTest {

    private val messageIdGenerator = mockk<MessageIdGenerator>()
    private val cut = InMemoryMessageRepository(messageIdGenerator)

    @Test
    fun `success to save direct message`() {
        // given
        val senderId = "senderId"
        val receiverId = "receiverId"
        val contents = "contents"
        val messageId = "messageId"

        every { messageIdGenerator.getNewDirectMessageId() } returns messageId

        // when
        val result = cut.saveDirectMessage(senderId = senderId, receiverId = receiverId, contents = contents)

        // then
        assertEquals(messageId, result)
    }

    @Test
    fun `success to save group message`() {
        // given
        val senderId = "senderId"
        val groupId = "groupId"
        val contents = "contents"
        val messageId = "messageId"

        every { messageIdGenerator.getNewGroupMessageId() } returns messageId

        // when
        val result = cut.saveGroupMessage(senderId = senderId, groupId = groupId, contents = contents)

        // then
        assertEquals(messageId, result)
    }

    @Test
    fun `success to find direct messages by user`() {
        // given
        val senderId = "senderId"
        val receiverId = "receiverId"
        val contents = "contents"
        val messageId = "messageId"

        every { messageIdGenerator.getNewDirectMessageId() } returns messageId
        cut.saveDirectMessage(senderId, receiverId, contents)

        // when
        val result = cut.findDirectMessagesByReceiverId(receiverId = receiverId)

        // then
        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(messageId, result.first().messageId)
    }

    @Test
    fun `success to find group messages by specific group`() {
        // given
        val senderId = "senderId"
        val groupId = "groupId"
        val contents = "contents"
        val messageId = "messageId"

        every { messageIdGenerator.getNewGroupMessageId() } returns messageId
        cut.saveGroupMessage(senderId, groupId, contents)

        // when
        val result = cut.findGroupMessagesByGroupId(groupId)

        // then
        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(messageId, result.first().messageId)
    }

    @Test
    fun `success to find group messages by groupIds`() {
        // given
        val senderId = "senderId"
        val receiverId = "receiverId"
        val contents = "contents"

        every { messageIdGenerator.getNewGroupMessageId() } returnsMany listOf("messageId1", "messageId2")

        cut.saveGroupMessage(senderId, "groupId1", contents)
        cut.saveGroupMessage(senderId, "groupId2", contents)

        // when
        val result = cut.findGroupMessagesByGroupIds(listOf("groupId1", "groupId2"))

        // then
        assertNotNull(result)
        assertEquals(2, result.size)
        assertEquals("messageId1", result[0].messageId)
        assertEquals("messageId2", result[1].messageId)
    }
}