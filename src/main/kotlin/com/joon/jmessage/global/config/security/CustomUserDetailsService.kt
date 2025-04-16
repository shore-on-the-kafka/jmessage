package com.joon.jmessage.global.config.security

import com.joon.jmessage.user.application.port.out.GetUserRepository
import com.joon.jmessage.user.domain.UserId
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val inMemoryUserRepository: GetUserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    override fun loadUserByUsername(userId: String?): UserDetails {
        val user = userId?.let { UserId(it) }?.let { inMemoryUserRepository.getUserById(it) }

        return User.builder()
            .username(user?.userId?.value)
            .password(user?.password)
            .roles(SpringSecurityConfig.UserRole.SIGNED_USER)
            .build()
    }
}