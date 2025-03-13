package com.wooteco.wiki.service

import com.wooteco.wiki.domain.Document
import com.wooteco.wiki.domain.Log
import com.wooteco.wiki.dto.DocumentCreateRequest
import com.wooteco.wiki.dto.DocumentFindAllByRecentResponse
import com.wooteco.wiki.dto.DocumentResponse
import com.wooteco.wiki.dto.DocumentUpdateRequest
import com.wooteco.wiki.exception.DocumentNotFoundException
import com.wooteco.wiki.exception.DuplicateDocumentException
import com.wooteco.wiki.repository.DocumentRepository
import com.wooteco.wiki.repository.LogRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.Optional
import java.util.Random

@Service
@Transactional
class DocumentService(
    private val documentRepository: DocumentRepository,
    private val logRepository: LogRepository,
) {
    private val random: Random = Random()

    fun post(request: DocumentCreateRequest): DocumentResponse {
        val (title, contents, writer, documentBytes) = request

        if (documentRepository.existsByTitle(title)) {
            throw DuplicateDocumentException("제목이 겹치는 문서가 있습니다.")
        }

        val document = Document(title, contents, writer, documentBytes, LocalDateTime.now())
        val savedDocument = documentRepository.save(document)

        val log = Log(title, contents, writer, documentBytes, savedDocument.generateTime)
        logRepository.save(log)

        return mapToResponse(savedDocument)
    }

    fun getRandom(): DocumentResponse {
        val documents = documentRepository.findAll()
        if (documents.isEmpty()) {
            throw DocumentNotFoundException("문서가 없습니다.")
        }
        val document = documents[random.nextInt(documents.size)]

        return mapToResponse(document)
    }

    fun get(title: String): Optional<DocumentResponse> =
        documentRepository.findByTitle(title).map { mapToResponse(it) }

    fun put(title: String, request: DocumentUpdateRequest): DocumentResponse {
        val (contents, writer, documentBytes) = request

        val document = documentRepository.findByTitle(title)
            .orElseThrow { DocumentNotFoundException("존재하지 않는 제목의 문서입니다.") }

        document.update(contents, writer, documentBytes, LocalDateTime.now())

        val log = Log(title, contents, writer, documentBytes, document.generateTime)
        logRepository.save(log)

        return mapToResponse(document)
    }

    fun getRecentDocuments(): DocumentFindAllByRecentResponse {
        val documents = documentRepository.findAllByOrderByGenerateTimeDesc()
        return DocumentFindAllByRecentResponse.of(documents)
    }

    private fun mapToResponse(document: Document): DocumentResponse =
        DocumentResponse(
            document.documentId ?: throw DocumentNotFoundException("문서 ID가 없습니다."),
            document.title,
            document.contents,
            document.writer,
            document.generateTime,
        )
}
