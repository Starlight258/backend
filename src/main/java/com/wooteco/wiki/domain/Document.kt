package com.wooteco.wiki.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Document(
    @Column(nullable = false, unique = true)
    var title: String = "",
    var contents: String = "",
    var writer: String = "",
    var documentBytes: Long = 0,
    var generateTime: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val documentId: Long? = null,
) {
    constructor(title: String, contents: String, writer: String, documentBytes: Long, generateTime: LocalDateTime)
            : this(title, contents, writer.toString(), documentBytes, generateTime, null)

    fun update(
        contents: String,
        writer: String,
        documentBytes: Long,
        generateTime: LocalDateTime
    ) = apply {
        this.contents = contents
        this.writer = writer
        this.documentBytes = documentBytes
        this.generateTime = generateTime
    }

    override fun equals(other: Any?): Boolean =
        other is Document && this.documentId == other.documentId;

    override fun hashCode(): Int = documentId?.hashCode() ?: 0
}
