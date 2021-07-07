package com.github.raketa92.mailservice.service

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@Service
class CustomMessageSender(@Autowired private val rabbitTemplate: RabbitTemplate) {

    fun sendMessage() {
        val message = Message(1, "sent")
        rabbitTemplate.convertAndSend(message)
        println("Published")
    }
}

data class Message(
        @JsonProperty("messageId") val messageId: Int,
        @JsonProperty("status") val status: String
) : Serializable