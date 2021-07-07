package com.github.raketa92.mailservice.service

import com.github.raketa92.mailservice.model.Email
import com.github.raketa92.mailservice.repository.MailRepository
import javassist.NotFoundException
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.repository.Query
import org.springframework.http.HttpStatus
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class EmailService(
        private val  mailRepository: MailRepository,
        private val javaMailSender: JavaMailSender) {

    fun sendMail(email: Email) {
        val message = SimpleMailMessage()

        message.setSubject(email.subject)
        message.setText(email.message!!)
        message.setTo(email.to)
        message.setFrom("no-reply@tasinyyldyz.com")

        javaMailSender.send(message)
        println("email sent")
    }

    suspend fun getAllEmails(): Flow<Email> = mailRepository.findAll()

    suspend fun getEmailById(id: Long): Email? = mailRepository.findById(id)

    suspend fun getEmailByMessageId(messageId: Int): Email? = mailRepository.getEmailByMessageId(messageId)

    suspend fun createEmail(email: Email): Email = mailRepository.save(email)

    suspend fun updateStatus(statusId: Int, id: Long): Email = mailRepository.updateStatus(statusId, id)


}