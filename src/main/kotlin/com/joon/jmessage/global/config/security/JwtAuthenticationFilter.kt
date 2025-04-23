package com.joon.jmessage.global.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthenticationFilter(
    @Value("\${jwt.secret-key}") private val secretKey: String
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // JWT 토큰 추출 및 검증 로직
        val token = extractJwtFromRequest(request)
        if (token != null) {
            val jwt = validateAndDecodeToken(token)
            if (jwt != null) {
                val authentication = getAuthentication(jwt)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun extractJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION)

        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }

    fun validateAndDecodeToken(token: String): DecodedJWT? {
        return try {
            JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token)
        } catch (e: JWTVerificationException) {
            null
        }
    }

    private fun getAuthentication(decodedJWT: DecodedJWT): Authentication? {
        return try {
            val username = decodedJWT.subject
            val roles = decodedJWT.getClaim("roles").asList(SpringSecurityConfig.UserRole::class.java)
            val authorities = roles.map { SimpleGrantedAuthority(it.toString()) }

            UsernamePasswordAuthenticationToken(username, null, authorities)
        } catch (exception: Exception) {
            null
        }
    }
}