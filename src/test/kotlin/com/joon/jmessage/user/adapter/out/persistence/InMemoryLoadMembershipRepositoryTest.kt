package com.joon.jmessage.user.adapter.out.persistence

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class InMemoryLoadMembershipRepositoryTest {

    private val cut = InMemoryLoadMembershipRepository()

    @Test
    fun `success to join group`() {
        // given
        val userId = "userId"
        val groupId = "groupId"

        // when
        cut.joinGroup(userId, groupId)

        // then
        val result = cut.getMembershipsByUserId(userId)
        assertEquals(1, result.size)
        assertEquals(userId, result[0].userId)
        assertEquals(groupId, result[0].groupId)
    }

    @Test
    fun `should return empty list if no memberships for user`() {
        // given
        val userId = "userId"

        // when
        val result = cut.getMembershipsByUserId(userId)

        // then
        assertEquals(0, result.size)
    }

    @Test
    fun `success to find joined memberships by user`() {
        // given
        val userId = "userId"
        val groupId1 = "group1"
        val groupId2 = "group2"

        cut.joinGroup(userId, groupId1)
        cut.joinGroup(userId, groupId2)

        // when
        val memberships = cut.getMembershipsByUserId(userId)

        // then
        assertEquals(2, memberships.size)
        assertEquals(setOf(groupId1, groupId2), memberships.map { it.groupId }.toSet())
    }
}