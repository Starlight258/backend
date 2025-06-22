package com.wooteco.wiki.document.service

import com.wooteco.wiki.document.domain.dto.DocumentSearchResponse
import com.wooteco.wiki.document.repository.DocumentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DocumentSearchService(
    private val documentRepository: DocumentRepository,
) {

    @Transactional(readOnly = true)
    fun search(keyWord: String): List<DocumentSearchResponse> =
        documentRepository.findAllByTitleStartingWith(keyWord)
            .map { DocumentSearchResponse(it.title, it.uuid) }
}
