package com.joon.jmessage.user.domain

data class UserId(val value: String) {
    init {
        require(value.isNotBlank()) { "User id must not be blank" }
    }
}
