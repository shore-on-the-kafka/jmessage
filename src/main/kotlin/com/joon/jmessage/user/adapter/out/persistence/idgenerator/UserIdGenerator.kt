package com.joon.jmessage.user.adapter.out.persistence.idgenerator

import com.joon.jmessage.user.domain.UserId
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserIdGenerator {

    fun getNewUserId(): UserId {
        return UserId("USER-${UUID.randomUUID()}")
    }
}