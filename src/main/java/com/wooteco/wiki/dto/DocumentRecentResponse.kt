package com.wooteco.wiki.dto

import com.wooteco.wiki.domain.Document
import java.time.LocalDateTime

data class DocumentRecentResponse(
    val documentId: Long?,
    val title: String,
    val generateTime: LocalDateTime,
) {
    companion object {
        fun from(document: Document) = DocumentRecentResponse(
            documentId = document.documentId,
            title = document.title,
            generateTime = document.generateTime,
        )
    }
}
