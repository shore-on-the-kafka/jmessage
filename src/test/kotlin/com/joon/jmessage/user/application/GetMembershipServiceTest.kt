package com.joon.jmessage.user.application

import com.joon.jmessage.user.application.port.out.LoadMembershipRepository
import com.joon.jmessage.user.domain.Membership
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GetMembershipServiceTest {

    private val loadMembershipRepository = mockk<LoadMembershipRepository>()
    private val cut = GetMembershipService(loadMembershipRepository)

    @Test
    fun `success to find joined memberships by user`() {
        // given
        val userId = "userId"
        val expected = listOf(Membership("membershipid", userId, "groupId", Instant.now()))

        every { loadMembershipRepository.getMembershipsByUserId(any()) } returns expected

        // when
        val result = cut.getMembershipsByUserId(userId = userId)

        // then
        assertNotNull(result)
        assertEquals(expected.size, result.size)
        assertEquals(expected.first(), result.first())
    }

    @Test
    fun `return empty list if no memberships joined`() {
        // given
        val userId = "userId"
        val expected = emptyList<Membership>()

        every { loadMembershipRepository.getMembershipsByUserId(any()) } returns expected

        // when
        val result = cut.getMembershipsByUserId(userId = userId)

        // then
        assertNotNull(result)
        assertEquals(0, result.size)
    }
}