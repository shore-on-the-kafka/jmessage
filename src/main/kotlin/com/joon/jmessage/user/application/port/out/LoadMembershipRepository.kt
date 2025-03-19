package com.joon.jmessage.user.application.port.out

import com.joon.jmessage.user.domain.Membership

interface LoadMembershipRepository {

    fun getMembershipsByUserId(userId: String): List<Membership>
}