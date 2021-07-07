package com.github.raketa92.mailservice.repository

import com.github.raketa92.mailservice.model.Email
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MailRepository : CoroutineCrudRepository<Email, Long> {
    @Query("SELECT * FROM email WHERE messageId = :messageId")
    suspend fun getEmailByMessageId(messageId: Int): Email?

    @Query("UPDATE email set statusId = :statusId where id = :id")
    suspend fun updateStatus(statusId: Int, id: Long): Email
}