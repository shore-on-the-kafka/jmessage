package com.joon.jmessage.message.application.port.out

import com.joon.jmessage.message.domain.DirectMessage
import com.joon.jmessage.message.domain.GroupMessage

interface LoadMessageRepository {
    fun findDirectMessagesByReceiverId(receiverId: String): List<DirectMessage>
    fun findGroupMessagesByGroupId(groupId: String): List<GroupMessage>
    fun findGroupMessagesByGroupIds(groupIds: List<String>): List<GroupMessage>
}