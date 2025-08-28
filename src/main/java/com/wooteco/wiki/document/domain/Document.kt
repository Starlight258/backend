package com.wooteco.wiki.document.domain

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import java.sql.Types
import java.time.LocalDateTime
import java.util.*

@Entity
class Document(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    var title: String = "",

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    var contents: String = "",

    var writer: String = "",
    
    var documentBytes: Long = 0,

    var generateTime: LocalDateTime = LocalDateTime.now(),

    @JdbcTypeCode(Types.CHAR)
    var uuid: UUID = UUID.randomUUID()
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
