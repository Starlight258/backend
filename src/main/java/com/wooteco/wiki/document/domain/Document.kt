package com.wooteco.wiki.document.domain

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import java.sql.Types
import java.time.LocalDateTime
import java.util.*

@Entity
class Document(
    @Column(nullable = false, unique = true)
    var title: String = "",

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    var contents: String = "",
    var writer: String = "",

    @Column(name = "document_bytes")
    var documentBytes: Long = 0,

    @Column(name = "generate_time")
    var generateTime: LocalDateTime = LocalDateTime.now(),

    @JdbcTypeCode(Types.CHAR)
    var uuid: UUID = UUID.randomUUID(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    fun update(
        title: String,
        contents: String,
        writer: String,
        documentBytes: Long,
        generateTime: LocalDateTime
    ) = apply {
        this.title = title
        this.contents = contents
        this.writer = writer
        this.documentBytes = documentBytes
        this.generateTime = generateTime
    }

    override fun equals(other: Any?): Boolean =
        other is Document && this.id == other.id;

    override fun hashCode(): Int = id?.hashCode() ?: 0
}
