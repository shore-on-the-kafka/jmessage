package com.joon.jmessage.user.application

import com.joon.jmessage.user.application.port.`in`.GetUserUseCase
import com.joon.jmessage.user.application.port.out.GetUserRepository
import com.joon.jmessage.user.domain.User
import com.joon.jmessage.user.domain.UserKey
import org.springframework.stereotype.Service

@Service
class GetUserService(
    private val inMemoryUserRepository: GetUserRepository
) : GetUserUseCase {

    override fun getUserByUserKey(userKey: UserKey): User? {
        inMemoryUserRepository.getUserByUserKey(userKey)?.let {
            return it
        }
        return null
    }
}