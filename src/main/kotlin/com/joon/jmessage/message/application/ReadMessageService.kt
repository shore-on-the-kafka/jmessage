package com.joon.jmessage.message.application

import com.joon.jmessage.message.application.exception.GroupException
import com.joon.jmessage.message.application.port.`in`.ReadMessageUseCase
import com.joon.jmessage.message.application.port.`in`.command.ReadAllMessagesCommand
import com.joon.jmessage.message.application.port.`in`.command.ReadGroupMessagesCommand
import com.joon.jmessage.message.application.port.out.LoadMessageRepository
import com.joon.jmessage.message.domain.GroupMessage
import com.joon.jmessage.message.domain.Message
import com.joon.jmessage.user.application.port.`in`.GetMembershipUseCase
import com.joon.jmessage.user.application.port.`in`.MembershipCheckUseCase
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class ReadMessageService(
    private val inMemoryMessageRepository: LoadMessageRepository,
    private val getMembershipService: GetMembershipUseCase,
    private val membershipCheckService: MembershipCheckUseCase
) : ReadMessageUseCase {
    override fun findSendedAllMessagesByUserId(senderId: String): List<Message> {
        TODO("Not yet implemented")
    }

    override fun findReceivedGroupMessagesByUserIdAndGroupId(readGroupMessagesCommand: ReadGroupMessagesCommand): List<GroupMessage> {
        val groupId = readGroupMessagesCommand.groupId
        val userId = readGroupMessagesCommand.userId

        val isJoined = membershipCheckService.isJoinedMembership(groupId = groupId, userId = userId)
        if (!isJoined) {
            GroupException.UnauthorizedException()
        }

        return inMemoryMessageRepository.findGroupMessagesByGroupId(groupId)
    }

    override fun findReceivedAllMessagesByUserId(readAllMessagesCommand: ReadAllMessagesCommand): List<Message> {
        val userId = readAllMessagesCommand.userId

        val receivedDirectMessages = inMemoryMessageRepository.findDirectMessagesByReceiverId(userId)

        val joinedGroupIds = getMembershipService.getMembershipsByUserId(userId).stream()
            .map { membership -> membership.groupId }
            .collect(Collectors.toList())
        val receivedAllGroupMessages = inMemoryMessageRepository.findGroupMessagesByGroupIds(joinedGroupIds)

        return receivedDirectMessages + receivedAllGroupMessages
    }
}