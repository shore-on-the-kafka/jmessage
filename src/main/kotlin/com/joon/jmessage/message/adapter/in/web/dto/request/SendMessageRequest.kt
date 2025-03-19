package com.joon.jmessage.message.adapter.`in`.web.dto.request

data class SendMessageRequest(
    val messageFrom: String,
    val messageTo: String,
    val contents: String
)