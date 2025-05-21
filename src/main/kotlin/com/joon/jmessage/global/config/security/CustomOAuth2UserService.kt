package com.joon.jmessage.global.config.security

import com.joon.jmessage.global.model.OAuth2UserInfo
import com.joon.jmessage.global.model.PrincipalDetails
import com.joon.jmessage.user.application.port.`in`.GetUserUseCase
import com.joon.jmessage.user.application.port.`in`.RegisterUserUseCase
import com.joon.jmessage.user.application.port.`in`.command.RegisterUserCommand
import com.joon.jmessage.user.domain.User
import com.joon.jmessage.user.domain.UserKey
import org.springframework.context.annotation.Lazy
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    @Lazy private val getUserUseCase: GetUserUseCase,
    @Lazy private val registerUserUseCase: RegisterUserUseCase
) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val oAuth2UserAttributes = super.loadUser(userRequest).attributes
        val registrationId = userRequest?.clientRegistration?.registrationId
            ?: throw IllegalArgumentException("Registration ID is null")
        val userNameAttributeName = userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName
            ?: throw IllegalArgumentException("Username attribute name is null")

        val oAuth2UserInfo = OAuth2UserInfo.of(registrationId, oAuth2UserAttributes)
        val user = getOrSave(oAuth2UserInfo)

        return PrincipalDetails(
            user = user,
            attributes = oAuth2UserAttributes,
            nameAttributeKey = userNameAttributeName
        )
    }

    private fun getOrSave(oAuth2UserInfo: OAuth2UserInfo): User {
        val userKey = UserKey.of(oAuth2UserInfo.userId, oAuth2UserInfo.oauthProvider)
        val existingUser = getUserUseCase.getUserByUserKey(userKey)

        return existingUser ?: saveNewUser(oAuth2UserInfo)
    }

    private fun saveNewUser(oAuth2UserInfo: OAuth2UserInfo): User {
        val command = RegisterUserCommand(
            userId = oAuth2UserInfo.userId,
            oauthProvider = oAuth2UserInfo.oauthProvider,
            name = oAuth2UserInfo.name,
        )
        val user = registerUserUseCase.registerUser(command)
        return user
    }
}