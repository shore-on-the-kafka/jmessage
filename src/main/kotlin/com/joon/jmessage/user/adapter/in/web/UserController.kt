package com.joon.jmessage.user.adapter.`in`.web

import com.joon.jmessage.user.adapter.`in`.web.dto.request.LoginUserRequest
import com.joon.jmessage.user.adapter.`in`.web.dto.request.RegisterUserRequest
import com.joon.jmessage.user.adapter.`in`.web.dto.response.LoginUserResponse
import com.joon.jmessage.user.adapter.`in`.web.dto.response.RegisterUserResponse
import com.joon.jmessage.user.application.LoginUserService
import com.joon.jmessage.user.application.port.`in`.LoginUserUseCase
import com.joon.jmessage.user.application.port.`in`.RegisterUserUseCase
import com.joon.jmessage.user.application.port.`in`.command.LoginUserCommand
import com.joon.jmessage.user.application.port.`in`.command.RegisterUserCommand
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val registerUserService: RegisterUserUseCase,
    private val loginUserService : LoginUserUseCase
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

        val userId = registerUserService.registerUser(registerUserCommand)
        return RegisterUserResponse.of(userId)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody user: LoginUserRequest
    ): LoginUserResponse {
        val loginUserCommand = LoginUserCommand(
            email = user.email,
            password = user.password
        )

        val userId = loginUserService.login(loginUserCommand)
        return LoginUserResponse(userId)
    }
}