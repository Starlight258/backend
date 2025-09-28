package com.wooteco.wiki.document.domain.dto

import com.wooteco.wiki.document.domain.CrewDocument

data class DocumentFindAllByRecentResponse(
    val documents: List<DocumentRecentResponse>
) {
    companion object {
        fun of(crewDocuments: List<CrewDocument?>): DocumentFindAllByRecentResponse {
            val recentDocuments = crewDocuments.filterNotNull().map { DocumentRecentResponse.from(it) }
            return DocumentFindAllByRecentResponse(recentDocuments)
        }
    }
}
