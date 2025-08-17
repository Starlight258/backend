package com.wooteco.wiki.document.service

import com.wooteco.wiki.document.domain.Document
import com.wooteco.wiki.document.domain.dto.DocumentCreateRequest
import com.wooteco.wiki.document.domain.dto.DocumentResponse
import com.wooteco.wiki.document.domain.dto.DocumentUpdateRequest
import com.wooteco.wiki.document.domain.dto.DocumentUuidResponse
import com.wooteco.wiki.document.repository.DocumentRepository
import com.wooteco.wiki.global.common.PageRequestDto
import com.wooteco.wiki.global.exception.ErrorCode
import com.wooteco.wiki.global.exception.WikiException
import com.wooteco.wiki.log.service.LogService
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*
import kotlin.random.Random

@Service
@Transactional
class DocumentService(
    private val documentRepository: DocumentRepository,
    private val logService: LogService,
    private val random: Random,
) {

    fun post(request: DocumentCreateRequest): DocumentResponse {
        val (title, contents, writer, documentBytes, uuid) = request

        if (documentRepository.existsByTitle(title)) {
            throw WikiException(ErrorCode.DOCUMENT_DUPLICATE)
        }

        val document = Document(title, contents, writer, documentBytes, LocalDateTime.now(), uuid)
        val savedDocument = documentRepository.save(document)
        logService.save(savedDocument)
        val latestVersion = logService.findLatestVersionByDocument(savedDocument);
        return mapToResponse(savedDocument, latestVersion);
    }

    fun getRandom(): DocumentResponse {
        val documents = documentRepository.findAll()
        if (documents.isEmpty()) {
            throw WikiException(ErrorCode.DOCUMENT_NOT_FOUND)
        }
        val document = documents[random.nextInt(documents.size)]
        val latestVersion = logService.findLatestVersionByDocument(document);

        return mapToResponse(document, latestVersion);
    }

    fun findAll(requestDto: PageRequestDto): Page<Document> {
        val pageable = requestDto.toPageable()
        return documentRepository.findAll(pageable)
    }

    fun get(title: String): DocumentResponse {
        val document = documentRepository.findByTitle(title)
            .orElseThrow { WikiException(ErrorCode.DOCUMENT_NOT_FOUND) }
        val latestVersion = logService.findLatestVersionByDocument(document);
        return mapToResponse(document, latestVersion);
    }

    fun getUuidByTitle(title: String): DocumentUuidResponse =
        documentRepository.findUuidByTitle(title)
            .map(::DocumentUuidResponse)
            .orElseThrow { WikiException(ErrorCode.DOCUMENT_NOT_FOUND) }

    fun getByUuid(uuid: UUID): DocumentResponse {
        val document = documentRepository.findByUuid(uuid)
            .orElseThrow { WikiException(ErrorCode.DOCUMENT_NOT_FOUND) }
        val latestVersion = logService.findLatestVersionByDocument(document);
        return mapToResponse(document, latestVersion);
    }

    fun put(uuid: UUID, request: DocumentUpdateRequest): DocumentResponse {
        val (title, contents, writer, documentBytes) = request

        val document = documentRepository.findByUuid(uuid)
            .orElseThrow { WikiException(ErrorCode.DOCUMENT_NOT_FOUND) }

        val updateData = document.update(title, contents, writer, documentBytes, LocalDateTime.now())

        logService.save(updateData)

        val latestVersion = logService.findLatestVersionByDocument(document);

        return mapToResponse(document, latestVersion);
    }

    fun deleteById(id: Long) {
        documentRepository.findById(id)
            .orElseThrow { WikiException(ErrorCode.DOCUMENT_NOT_FOUND) }
        documentRepository.deleteById(id)
    }

    private fun mapToResponse(document: Document, latestVersion: Long): DocumentResponse =
        DocumentResponse(
            document.id ?: throw WikiException(ErrorCode.DOCUMENT_NOT_FOUND),
            document.uuid,
            document.title,
            document.contents,
            document.writer,
            document.generateTime,
            document.viewCount,
            latestVersion
        )

    fun flushViews(views: Map<UUID, Int>) {
        val documents = documentRepository.findAllByUuidIn(views.keys)

        for (document in documents) {
            val countToAdd = views[document.uuid] ?: continue
            document.viewCount += countToAdd
        }

        documentRepository.saveAll(documents)
    }
}
