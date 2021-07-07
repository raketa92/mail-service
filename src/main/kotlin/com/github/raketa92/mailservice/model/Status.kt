package com.github.raketa92.mailservice.model

import javax.persistence.*

@Entity
@Table(name = "status")
data class Status(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        @Column(nullable = false)
        val status: String,

        @OneToMany
        @JoinTable(name = "email", joinColumns = [JoinColumn(name = "id")], inverseJoinColumns = [JoinColumn(name = "statusId")])
        val emails: MutableList<Email>
)