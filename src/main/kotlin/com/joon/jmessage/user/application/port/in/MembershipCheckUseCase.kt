package com.joon.jmessage.user.application.port.`in`

interface MembershipCheckUseCase {
    fun isJoinedMembership(groupId: String, userId: String): Boolean
}