package com.joon.jmessage.global.model

import com.joon.jmessage.user.domain.User
import com.joon.jmessage.user.domain.User.OauthProvider.*

data class OAuth2UserInfo(
    val userId: String,
    val oauthProvider: User.OauthProvider,
    val name: String,
) {
    companion object {
        fun of(registrationId: String, attributes: Map<String, Any>): OAuth2UserInfo {
            return when (registrationId) {
                "line" -> ofLine(attributes)
                else -> throw IllegalArgumentException("Invalid registrationId: $registrationId")
            }
        }

        private fun ofLine(attributes: Map<String, Any>): OAuth2UserInfo {
            return OAuth2UserInfo(
                userId = attributes["userId"] as String,
                oauthProvider = LINE,
                name = attributes["displayName"] as String,
            )
        }
    }
}
