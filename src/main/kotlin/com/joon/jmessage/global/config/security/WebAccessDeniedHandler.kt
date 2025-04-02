package com.joon.jmessage.global.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.joon.jmessage.global.model.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class WebAccessDeniedHandler(
    private val objectMapper: ObjectMapper
) : AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: org.springframework.security.access.AccessDeniedException?
    ) {
        val errorResponse = ErrorResponse(
            status = HttpStatus.FORBIDDEN.value(),
            error = "Access Denied",
            message = "Access is denied"
        )

        response?.status = HttpStatus.FORBIDDEN.value()
        response?.contentType = "application/json"
        response?.writer?.write(objectMapper.writeValueAsString(errorResponse))
    }
}