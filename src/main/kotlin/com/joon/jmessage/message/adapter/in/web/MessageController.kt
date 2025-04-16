package com.joon.jmessage.message.adapter.`in`.web

import com.joon.jmessage.message.adapter.`in`.web.dto.request.SendMessageRequest
import com.joon.jmessage.message.adapter.`in`.web.dto.response.MessageViewResponse
import com.joon.jmessage.message.adapter.`in`.web.dto.response.SendMessageResponse
import com.joon.jmessage.message.application.port.`in`.ReadMessageUseCase
import com.joon.jmessage.message.application.port.`in`.SendMessageUseCase
import com.joon.jmessage.message.application.port.`in`.command.ReadAllMessagesCommand
import com.joon.jmessage.message.application.port.`in`.command.ReadGroupMessagesCommand
import com.joon.jmessage.message.application.port.`in`.command.SendMessageCommand
import com.joon.jmessage.message.domain.Message
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
@RequestMapping("/v1/messages")
class MessageController(
    private val sendMessageService: SendMessageUseCase,
    private val readMessageService: ReadMessageUseCase
) {

    @PostMapping
    fun sendMessage(
        @RequestHeader("X-User-Id") userId: String,
        @RequestBody message: SendMessageRequest,
    ): SendMessageResponse {
        val sendMessageCommand = SendMessageCommand.of(
            userId = userId,
            messageFrom = message.messageFrom,
            messageTo = message.messageTo,
            contents = message.contents
        )

        val messageId = sendMessageService.sendMessage(sendMessageCommand)
        return SendMessageResponse.of(messageId)
    }

    @GetMapping
    fun getReceivedAllMessages(
        @RequestHeader("X-User-Id") userId: String
    ): List<MessageViewResponse> {
        val messages = readMessageService.findReceivedAllMessagesByUserId(ReadAllMessagesCommand(userId = userId))

        return messages.stream()
            .map { message -> MessageViewResponse.of(message) }
            .collect(Collectors.toList())
    }

    @GetMapping("/new")
    fun getReceivedNewMessages(
        @RequestHeader("X-User-Id") receiverId: String
    ): List<Message> {
        TODO()
    }

    @GetMapping("/{groupId}")
    fun getReceivedGroupMessages(
        @RequestHeader("X-User-Id") userId: String,
        @PathVariable groupId: String
    ): List<MessageViewResponse> {
        val messages = readMessageService.findReceivedGroupMessagesByUserIdAndGroupId(ReadGroupMessagesCommand(groupId = groupId, userId = userId))

        return messages.stream()
            .map { message -> MessageViewResponse.of(message) }
            .collect(Collectors.toList())
    }
}