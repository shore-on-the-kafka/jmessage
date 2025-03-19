package com.joon.jmessage.user.application

import com.joon.jmessage.user.application.port.`in`.GetMembershipUseCase
import com.joon.jmessage.user.application.port.out.LoadMembershipRepository
import com.joon.jmessage.user.domain.Membership
import org.springframework.stereotype.Service

@Service
class GetMembershipService(
    private val inMemoryMembershipRepository: LoadMembershipRepository
) : GetMembershipUseCase {

    override fun getMembershipsByUserId(userId: String): List<Membership> {
        return inMemoryMembershipRepository.getMembershipsByUserId(userId)
    }
}