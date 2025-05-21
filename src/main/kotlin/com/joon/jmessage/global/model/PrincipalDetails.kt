package com.joon.jmessage.global.model

import com.joon.jmessage.global.config.security.SpringSecurityConfig
import com.joon.jmessage.user.domain.User
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

data class PrincipalDetails(
    private val user : User,
    private val attributes: Map<String, Any>,
    private val nameAttributeKey: String
) : OAuth2User, UserDetails {

    override fun getAuthorities() = listOf(SimpleGrantedAuthority(SpringSecurityConfig.SIGNED_USER))

    override fun getPassword() = null

    override fun getUsername() = user.userKey.value

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true

    override fun getAttributes(): Map<String, Any> = attributes

    override fun getName(): String = attributes[nameAttributeKey].toString()
}
