package com.joon.jmessage.global.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SpringSecurityConfig(
    private val customAuthorizationManager: CustomAuthorizationManager,
    private val webAccessDeniedHandler: WebAccessDeniedHandler,
    private val webAuthenticationEntryPoint: WebAuthenticationEntryPoint
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/**").access(customAuthorizationManager)
                    .anyRequest().authenticated()
            }
            .formLogin { it.disable() }
            .httpBasic {} // HTTP Basic 인증 활성화
            .exceptionHandling {
                it.accessDeniedHandler(webAccessDeniedHandler) // 403 Handling
                    .authenticationEntryPoint(webAuthenticationEntryPoint) // 401 Handling
            }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    companion object UserRole {
        const val ADMIN = "ADMIN"
        const val SIGNED_USER = "SIGNED_USER"
    }
}