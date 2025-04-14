package com.wooteco.wiki.dto

import java.time.LocalDateTime

data class DocumentResponse(
    val documentId: Long,
    val title: String,
    val contents: String,
    val writer: String,
    val generateTime: LocalDateTime,
)
