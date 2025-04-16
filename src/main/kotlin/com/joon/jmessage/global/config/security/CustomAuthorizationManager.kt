package com.joon.jmessage.global.config.security

import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.access.intercept.RequestAuthorizationContext
import org.springframework.stereotype.Component
import java.util.function.Supplier

@Component
class CustomAuthorizationManager : AuthorizationManager<RequestAuthorizationContext> {

    override fun check(
        authenticationSupplier: Supplier<Authentication?>,
        context: RequestAuthorizationContext
    ): AuthorizationDecision {
        val authentication = authenticationSupplier.get()

        if (authentication == null || !authentication.isAuthenticated) {
            return AuthorizationDecision(false)
        }

        val request = context.request
        if (request.requestURI.startsWith("/v1/messages")) {
            val isSignedUser = authentication.authorities.any { it.authority == "ROLE_${SpringSecurityConfig.SIGNED_USER}" }
            return AuthorizationDecision(isSignedUser)
        }

        return AuthorizationDecision(true)
    }
}