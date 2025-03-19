package com.joon.jmessage.user.adapter.`in`.web

import com.joon.jmessage.user.adapter.`in`.web.dto.request.RegisterUserRequest
import com.joon.jmessage.user.adapter.`in`.web.dto.response.RegisterUserResponse
import com.joon.jmessage.user.application.port.`in`.RegisterUserUseCase
import com.joon.jmessage.user.application.port.`in`.command.RegisterUserCommand
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val RegisterUserService: RegisterUserUseCase
) {

    @PostMapping
    fun registerUser(
        @RequestBody user: RegisterUserRequest
    ): RegisterUserResponse {
        val registerUserCommand = RegisterUserCommand(
            name = user.name,
            email = user.email,
            password = user.password
        )

        val userId = RegisterUserService.registerUser(registerUserCommand)
        return RegisterUserResponse.of(userId)
    }
}