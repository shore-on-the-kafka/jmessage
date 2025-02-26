package com.joon.jmessage.user.domain

import java.time.Instant

data class Membership(
    val membershipId: String,
    val userId: String,
    val groupId: String,
    val joinedAt: Instant,
)