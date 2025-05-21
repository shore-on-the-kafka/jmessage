package com.joon.jmessage.global.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SpringSecurityConfig(
    private val customAuthorizationManager: CustomAuthorizationManager,
    private val webAccessDeniedHandler: WebAccessDeniedHandler,
    private val webAuthenticationEntryPoint: WebAuthenticationEntryPoint,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val oauth2SuccessHandler: OAuth2SuccessHandler,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/**").access(customAuthorizationManager)
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .exceptionHandling {
                it.accessDeniedHandler(webAccessDeniedHandler) // 403 Handling
                    .authenticationEntryPoint(webAuthenticationEntryPoint) // 401 Handling
            }
            // oauth
            .oauth2Login { oAuth2LoginConfigurer ->
                oAuth2LoginConfigurer.userInfoEndpoint {
                    it.userService(customOAuth2UserService)
                }
                oAuth2LoginConfigurer.redirectionEndpoint {
                    it.baseUri("/oauth2/authorized/line")
                }
                oAuth2LoginConfigurer.successHandler(oauth2SuccessHandler)
            }

        return http.build()
    }

    companion object UserRole {
        const val ADMIN = "ADMIN"
        const val SIGNED_USER = "SIGNED_USER"
    }
}