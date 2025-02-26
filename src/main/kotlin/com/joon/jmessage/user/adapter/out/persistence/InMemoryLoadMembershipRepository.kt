package com.joon.jmessage.user.adapter.out.persistence

import com.joon.jmessage.user.application.port.out.LoadMembershipRepository
import com.joon.jmessage.user.application.port.out.MembershipJoinRepository
import com.joon.jmessage.user.domain.Membership
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Repository
class InMemoryLoadMembershipRepository(
) : LoadMembershipRepository,
    MembershipJoinRepository {
    private val memberships = mutableMapOf<String, Membership>()

    override fun joinGroup(userId: String, groupId: String) {
        val membershipId = generateMembershipId()
        val membership = Membership(
            membershipId = membershipId,
            userId = userId,
            groupId = groupId,
            Instant.now(),
        )
        memberships[membershipId] = membership
    }

    override fun getMembershipsByUserId(userId: String): List<Membership> {
        return memberships.values
            .filter { it.userId == userId }
    }

    private fun generateMembershipId(): String {
        return "MEMBERSHIP-${UUID.randomUUID()}"
    }
}