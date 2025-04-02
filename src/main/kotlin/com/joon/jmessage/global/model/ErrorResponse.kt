package com.joon.jmessage.global.model

data class ErrorResponse(
    val status: Int,
    val error: String,
    val message: String
)