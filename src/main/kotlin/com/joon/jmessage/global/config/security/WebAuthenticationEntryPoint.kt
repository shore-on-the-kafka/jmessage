package com.joon.jmessage.global.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.joon.jmessage.global.model.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class WebAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        val errorResponse = ErrorResponse(
            status = HttpStatus.UNAUTHORIZED.value(),
            error = "Unauthorized",
            message = "Authentication is required"
        )

        response?.status = HttpStatus.UNAUTHORIZED.value()
        response?.contentType = "application/json"
        response?.writer?.write(objectMapper.writeValueAsString(errorResponse))
    }
}