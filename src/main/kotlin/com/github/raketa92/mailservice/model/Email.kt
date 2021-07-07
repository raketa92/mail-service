package com.github.raketa92.mailservice.model

import javax.persistence.*

@Entity
@Table(name = "email")
data class Email (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @Column(nullable = false)
        val service: String,

        @Column(nullable = false)
        val messageId: Int,

        @Column(nullable = false)
        val to: String,

        @Column(nullable = false)
        val subject: String,

        @Column(nullable = false)
        val template: String,

        val attachment: String?,

        val message: String?,

        @Column(nullable = false)
        val statusId: Int,

        @ManyToOne
        @JoinColumn(name = "id", nullable = false)
        val status: Status,

        val errorMessage: String?
)
