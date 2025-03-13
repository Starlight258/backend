package com.wooteco.wiki.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Document(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var documentId: Long? = null,

    @Column(unique = true)
    var title: String,

    var contents: String,
    var writer: String,
    var documentBytes: Long,
    var generateTime: LocalDateTime,
) {
    fun update(contents: String, writer: String, documentBytes: Long, generateTime: LocalDateTime) {
        this.contents = contents
        this.writer = writer
        this.documentBytes = documentBytes
        this.generateTime = generateTime
    }

    constructor() : this(null, "", "", "", 0, LocalDateTime.now())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Document) return false
        return documentId != null && documentId == other.documentId
    }

    override fun hashCode(): Int = documentId?.hashCode() ?: 0

    companion object {
        @JvmStatic
        fun builder() = Builder()
    }

    class Builder {
        private var documentId: Long? = null
        private var title: String = ""
        private var contents: String = ""
        private var writer: String = ""
        private var documentBytes: Long = 0
        private var generateTime: LocalDateTime = LocalDateTime.now()

        fun documentId(documentId: Long) = apply { this.documentId = documentId }
        fun title(title: String) = apply { this.title = title }
        fun contents(contents: String) = apply { this.contents = contents }
        fun writer(writer: String) = apply { this.writer = writer }
        fun documentBytes(documentBytes: Long) = apply { this.documentBytes = documentBytes }
        fun generateTime(generateTime: LocalDateTime) = apply { this.generateTime = generateTime }

        fun build() = Document(documentId, title, contents, writer, documentBytes, generateTime)
    }
}
