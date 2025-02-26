package com.joon.jmessage.message.application.port.out

interface SaveMessageRepository {
    fun saveDirectMessage(senderId: String, receiverId: String, contents: String): String
    fun saveGroupMessage(senderId: String, groupId: String, contents: String): String
}