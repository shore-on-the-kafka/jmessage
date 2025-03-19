package com.joon.jmessage.user.application.internal

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoder() {
    private val bCryptPasswordEncoder = BCryptPasswordEncoder()

    fun encode(password: String): String {
        return bCryptPasswordEncoder.encode(password)
    }

    fun matches(inputPassword: String, encodedPassword: String): Boolean {
        return bCryptPasswordEncoder.matches(inputPassword, encodedPassword)
    }
}