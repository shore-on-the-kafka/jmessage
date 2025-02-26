package com.joon.jmessage.user.application

import com.joon.jmessage.user.application.port.`in`.MembershipCheckUseCase
import com.joon.jmessage.user.application.port.out.LoadMembershipRepository
import org.springframework.stereotype.Service

@Service
class MembershipCheckService(
    private val inMemoryMembershipRepository: LoadMembershipRepository
) : MembershipCheckUseCase {

    override fun isJoinedMembership(groupId: String, userId: String): Boolean {
        val joinedMembershipsByUser = inMemoryMembershipRepository.getMembershipsByUserId(userId)

        return joinedMembershipsByUser.stream()
            .filter { membership -> membership.groupId == groupId }
            .findFirst()
            .isPresent
    }
}