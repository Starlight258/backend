package com.wooteco.wiki.document.domain.dto

import com.wooteco.wiki.organizationdocument.dto.response.OrganizationDocumentResponse
import java.time.LocalDateTime
import java.util.*

data class DocumentResponse(
    val documentId: Long,
    var documentUUID: UUID,
    val title: String,
    val contents: String,
    val writer: String,
    val generateTime: LocalDateTime,
    val latestVersion: Long,
    val organizationDocumentResponses : List<OrganizationDocumentResponse>
)
