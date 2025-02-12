package com.joon.jmessage.message.adapter.`in`.web

import com.joon.jmessage.message.adapter.`in`.web.model.request.SendMessageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/messages")
class MessageController {

    @PostMapping
    fun sendMessage(
        @RequestBody message: SendMessageRequest,
    ): String {
        return "Message: $message"
    }

    @GetMapping
    fun getMessages(
    ): List<String> {
        return listOf("Message1", "Message2")
    }
}