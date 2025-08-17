package com.wooteco.wiki.document.domain.dto

import java.time.LocalDateTime
import java.util.*

data class DocumentResponse(
    val documentId: Long,
    var documentUUID: UUID,
    val title: String,
    val contents: String,
    val writer: String,
    val generateTime: LocalDateTime,
    val latestVersion: Long
)
