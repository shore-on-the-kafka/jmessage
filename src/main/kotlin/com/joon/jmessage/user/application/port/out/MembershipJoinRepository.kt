package com.joon.jmessage.user.application.port.out

interface MembershipJoinRepository {
    fun joinGroup(userId: String, groupId: String)
}