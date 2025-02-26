package com.joon.jmessage.message.application

import com.joon.jmessage.message.application.port.`in`.command.ReadAllMessagesCommand
import com.joon.jmessage.message.application.port.`in`.command.ReadGroupMessagesCommand
import com.joon.jmessage.message.application.port.out.LoadMessageRepository
import com.joon.jmessage.message.domain.DirectMessage
import com.joon.jmessage.message.domain.GroupMessage
import com.joon.jmessage.user.application.port.`in`.GetMembershipUseCase
import com.joon.jmessage.user.application.port.`in`.MembershipCheckUseCase
import com.joon.jmessage.user.domain.Membership
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ReadMessageServiceTest {

    private val loadMessageRepository = mockk<LoadMessageRepository>()
    private val getMembershipUseCase = mockk<GetMembershipUseCase>()
    private val membershipCheckUseCase = mockk<MembershipCheckUseCase>()
    private val cut = ReadMessageService(loadMessageRepository, getMembershipUseCase, membershipCheckUseCase)

    @Test
    fun `success to find received specific group messages by user`() {
        // given
        val groupId = "groupId"
        val userId = "userId"
        val command = ReadGroupMessagesCommand(groupId = groupId, userId = userId)

        val expected = listOf(GroupMessage("message1", "contents", "senderId", Instant.now(), "groupId"))

        every { membershipCheckUseCase.isJoinedMembership(groupId, userId) } returns true
        every { loadMessageRepository.findGroupMessagesByGroupId(groupId) } returns expected

        // when
        val result = cut.findReceivedGroupMessagesByUserIdAndGroupId(command)

        // then
        assertNotNull(result)
        assertEquals(expected.size, result.size)
        assertEquals(expected.first(), result.first())
    }

    @Test
    fun `success to find received all messages by user`() {
        // given
        val userId = "userId"
        val command = ReadAllMessagesCommand(userId = userId)
        val joinedMemberships = listOf(Membership("membershipId", userId, "groupId", Instant.now()))

        val groupMessages = listOf(GroupMessage("message1", "contents", "senderId", Instant.now(), "groupId"))
        val directMessages = listOf(DirectMessage("message1", "contents", "senderId", Instant.now(), "userId"))

        every { getMembershipUseCase.getMembershipsByUserId(any()) } returns joinedMemberships
        every { loadMessageRepository.findGroupMessagesByGroupIds(any()) } returns groupMessages
        every { loadMessageRepository.findDirectMessagesByReceiverId(any()) } returns directMessages

        // when
        val result = cut.findReceivedAllMessagesByUserId(command)

        // then
        assertNotNull(result)
        assertEquals(groupMessages.size + directMessages.size, result.size)
    }
}