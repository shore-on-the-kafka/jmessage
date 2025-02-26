package com.joon.jmessage.message.adapter.out.persistence.idgenerator

import org.springframework.stereotype.Component
import java.util.*

@Component
class MessageIdGenerator {

    fun getNewDirectMessageId(): String {
        return "DM-${UUID.randomUUID()}"
    }

    fun getNewGroupMessageId(): String {
        return "GROUP-${UUID.randomUUID()}"
    }
}