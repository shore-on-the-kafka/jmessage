package com.joon.jmessage.global.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2SuccessHandler(
    private val jwtService: JwtService
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        // Handle successful authentication
        val userId = authentication.name
        val authorities = authentication.authorities.stream()
            .map { it.authority }
            .toList()

        val accessToken = jwtService.generateToken(userId, authorities)
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_OK

        // Write the accessToken to the response body
        val responseBody = mapOf("accessToken" to accessToken)
        response.writer.write(ObjectMapper().writeValueAsString(responseBody))
        response.writer.flush()
    }
}