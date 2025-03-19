package com.joon.jmessage.message.adapter.out.persistence

import com.joon.jmessage.message.adapter.out.persistence.idgenerator.MessageIdGenerator
import com.joon.jmessage.message.application.port.out.LoadMessageRepository
import com.joon.jmessage.message.application.port.out.SaveMessageRepository
import com.joon.jmessage.message.domain.DirectMessage
import com.joon.jmessage.message.domain.GroupMessage
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class InMemoryMessageRepository(
    private val messageIdGenerator: MessageIdGenerator
) : SaveMessageRepository,
    LoadMessageRepository {
    private val directMessages = mutableMapOf<String, DirectMessage>()
    private val groupMessages = mutableMapOf<String, GroupMessage>()
    //TODO - 실제 DB 붙인 이후에는 directMessageRepository, groupMessageRepository 구분
    override fun saveDirectMessage(senderId: String, receiverId: String, contents: String): String {
        val directMessageId = messageIdGenerator.getNewDirectMessageId()
        val directMessage = DirectMessage(
            messageId = directMessageId,
            contents = contents,
            senderId = senderId,
            receiverId = receiverId,
            createdAt = Instant.now()
        )
        directMessages[directMessageId] = directMessage
        return directMessageId
    }

    override fun saveGroupMessage(senderId: String, groupId: String, contents: String): String {
        val groupMessageId = messageIdGenerator.getNewGroupMessageId()
        val groupMessage = GroupMessage(
            messageId = groupMessageId,
            contents = contents,
            senderId = senderId,
            groupId = groupId,
            createdAt = Instant.now()
        )
        groupMessages[groupMessageId] = groupMessage
        return groupMessageId
    }

    override fun findDirectMessagesByReceiverId(receiverId: String): List<DirectMessage> {
        return directMessages.values
            .filter { it.receiverId == receiverId }
    }

    override fun findGroupMessagesByGroupId(groupId: String): List<GroupMessage> {
        return groupMessages.values
            .filter { it.groupId == groupId }
    }

    override fun findGroupMessagesByGroupIds(groupIds: List<String>): List<GroupMessage> {
        return groupMessages.values
            .filter { groupIds.contains(it.groupId) }
    }
}