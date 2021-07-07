package com.github.raketa92.mailservice.configuration

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfiguration {

    private val exchange_name = "mailExchange"
    private val routing_key = "mail_status_updated"
    private val queue_name = "mail_status_updated"

    @Bean
    fun eventExchange(): TopicExchange = TopicExchange(exchange_name, true, false)

    @Bean
    fun jackson2JsonMessageConverter(): Jackson2JsonMessageConverter = Jackson2JsonMessageConverter()

//    @Bean
//    fun queue(): Queue = Queue(queue_name, true)

//    @Bean
//    fun binding(queue: Queue, eventExchange: TopicExchange): Binding =
//            BindingBuilder.bind(queue).to(eventExchange).with(routing_key)

    @Bean
    fun rabbitTemplate(
            connectionFactory: ConnectionFactory,
            eventExchange: TopicExchange,
            jackson2JsonMessageConverter: Jackson2JsonMessageConverter
    ): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.setExchange(eventExchange.name)
        rabbitTemplate.routingKey = routing_key
        rabbitTemplate.messageConverter = jackson2JsonMessageConverter
        return rabbitTemplate
    }

}