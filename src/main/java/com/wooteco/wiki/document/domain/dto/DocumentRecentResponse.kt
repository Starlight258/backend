package com.wooteco.wiki.document.domain.dto

import com.wooteco.wiki.document.domain.CrewDocument
import java.time.LocalDateTime
import java.util.UUID

data class DocumentRecentResponse(
    val documentId: Long?,
    val uuid: UUID,
    val title: String,
    val generateTime: LocalDateTime,
) {
    companion object {
        fun from(crewDocument: CrewDocument) = DocumentRecentResponse(
            documentId = crewDocument.id,
            uuid = crewDocument.uuid,
            title = crewDocument.title,
            generateTime = crewDocument.generateTime,
        )
    }
}
