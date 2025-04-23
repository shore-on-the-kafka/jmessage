package com.joon.jmessage.user.adapter.`in`.web

import com.joon.jmessage.global.config.security.JwtService
import com.joon.jmessage.global.config.security.SpringSecurityConfig
import com.joon.jmessage.global.config.security.SpringSecurityConfig.*
import com.joon.jmessage.user.adapter.`in`.web.dto.request.LoginUserRequest
import com.joon.jmessage.user.adapter.`in`.web.dto.request.RegisterUserRequest
import com.joon.jmessage.user.adapter.`in`.web.dto.response.LoginUserResponse
import com.joon.jmessage.user.adapter.`in`.web.dto.response.RegisterUserResponse
import com.joon.jmessage.user.application.port.`in`.LoginUserUseCase
import com.joon.jmessage.user.application.port.`in`.RegisterUserUseCase
import com.joon.jmessage.user.application.port.`in`.command.LoginUserCommand
import com.joon.jmessage.user.application.port.`in`.command.RegisterUserCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val registerUserService: RegisterUserUseCase,
    private val loginUserService : LoginUserUseCase,
    private val jwtService: JwtService
) {

    @PostMapping
    fun registerUser(
        @RequestBody user: RegisterUserRequest
    ): ResponseEntity<RegisterUserResponse> {
        val registerUserCommand = RegisterUserCommand(
            name = user.name,
            email = user.email,
            password = user.password
        )
        val userId = registerUserService.registerUser(registerUserCommand)
        val accessToken = jwtService.generateToken(userId.value, listOf(UserRole.SIGNED_USER))

        return ResponseEntity.ok(RegisterUserResponse.of(userId, accessToken))
    }

    @PostMapping("/login")
    fun login(
        @RequestBody user: LoginUserRequest
    ): ResponseEntity<LoginUserResponse> {
        val loginUserCommand = LoginUserCommand(
            email = user.email,
            password = user.password
        )
        val userId = loginUserService.login(loginUserCommand)
        val accessToken = jwtService.generateToken(userId.value, listOf(UserRole.SIGNED_USER))

        return ResponseEntity.ok(LoginUserResponse.of(userId, accessToken))
    }
}