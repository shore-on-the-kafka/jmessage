package com.joon.jmessage.global.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService(
    @Value("\${jwt.secret-key}") private val secretKey: String,
    @Value("\${jwt.access-token-expire-in}") private val tokenExpireIn: Long
) {

    fun generateToken(userId: String, roles: List<String>): String {
        val algorithm = Algorithm.HMAC256(secretKey)
        val now = Date()
        val expireAt = Date(now.time + tokenExpireIn)

        return JWT.create()
            .withSubject(userId)
            .withIssuedAt(now)
            .withExpiresAt(expireAt)
            .withClaim("roles", roles)
            .sign(algorithm)
    }
}