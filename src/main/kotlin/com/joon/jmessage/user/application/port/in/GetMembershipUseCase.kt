package com.joon.jmessage.user.application.port.`in`

import com.joon.jmessage.user.domain.Membership

interface GetMembershipUseCase {
    fun getMembershipsByUserId(userId: String): List<Membership>
}