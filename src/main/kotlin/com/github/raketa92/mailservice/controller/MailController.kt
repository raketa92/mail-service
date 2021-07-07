package com.github.raketa92.mailservice.controller

import com.github.raketa92.mailservice.model.Email
import com.github.raketa92.mailservice.service.CustomMessageSender
import com.github.raketa92.mailservice.service.EmailService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.annotation.PreDestroy


@RestController
class MailController(private val emailService: EmailService,
                     private val msgSender: CustomMessageSender) {

    private val coroutineScope = CoroutineScope(SupervisorJob())

    @PostMapping("/api/sendemail")
    fun sendEmail(@RequestBody email: Email): String {
        coroutineScope.launch(Dispatchers.IO) {
                try {
                    emailService.sendMail(email)
                    emailService.createEmail(email)
                    msgSender.sendMessage()
                    emailService.updateStatus(1, email.id)
                } catch (e: Exception) {
                    println(e)
                }
            }
        return "email queued"
    }

    @PreDestroy
    fun cancelScope() {
        coroutineScope.cancel()
    }

    @GetMapping("/api/emails")
    suspend fun getAllEmails(): Flow<Email> = emailService.getAllEmails()

    @GetMapping("/api/email/{id}")
    suspend fun getEmailById(@PathVariable("id") id: Long): Email? = emailService.getEmailById(id)

    @GetMapping("/api/email/messageId/{id}")
    suspend fun getEmailByMessageId(@PathVariable("id") id: Int): Email? = emailService.getEmailByMessageId(id)

    @PostMapping("/api/email")
    suspend fun createEmail(email: Email): Email = emailService.createEmail(email)
}