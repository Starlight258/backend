package com.wooteco.wiki.document.service

import com.wooteco.wiki.document.domain.CrewDocument
import com.wooteco.wiki.document.domain.dto.DocumentCreateRequest
import com.wooteco.wiki.document.domain.dto.DocumentResponse
import com.wooteco.wiki.document.domain.dto.DocumentUpdateRequest
import com.wooteco.wiki.document.domain.dto.DocumentUuidResponse
import com.wooteco.wiki.document.repository.DocumentRepository
import com.wooteco.wiki.global.common.PageRequestDto
import com.wooteco.wiki.global.exception.ErrorCode
import com.wooteco.wiki.global.exception.WikiException
import com.wooteco.wiki.log.service.LogService
import com.wooteco.wiki.organizationdocument.service.DocumentOrganizationLinkService
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
    private val organizationDocumentLinkService: DocumentOrganizationLinkService,
    private val logService: LogService,
    private val random: Random,
) {

    fun post(request: DocumentCreateRequest): DocumentResponse {
        val (title, contents, writer, documentBytes, uuid) = request

        if (documentRepository.existsByTitle(title)) {
            throw WikiException(ErrorCode.DOCUMENT_DUPLICATE)
        }

        val crewDocument = CrewDocument(null, title, contents, writer, documentBytes, LocalDateTime.now(), uuid)

        val savedDocument = documentRepository.save(crewDocument)
        logService.save(savedDocument)
        return mapToResponse(savedDocument);
    }

    fun getRandom(): DocumentResponse {
        val documents = documentRepository.findAll()
        if (documents.isEmpty()) {
            throw WikiException(ErrorCode.DOCUMENT_NOT_FOUND)
        }
        val document = documents[random.nextInt(documents.size)]
        return mapToResponse(document);
    }

    fun findAll(requestDto: PageRequestDto): Page<CrewDocument> {
        val pageable = requestDto.toPageable()
        return documentRepository.findAll(pageable)
    }

    fun get(title: String): DocumentResponse {
        val document = documentRepository.findByTitle(title)
            .orElseThrow { WikiException(ErrorCode.DOCUMENT_NOT_FOUND) }
        return mapToResponse(document);
    }

    fun getUuidByTitle(title: String): DocumentUuidResponse =
        documentRepository.findUuidByTitle(title)
            .map(::DocumentUuidResponse)
            .orElseThrow { WikiException(ErrorCode.DOCUMENT_NOT_FOUND) }

    fun getByUuid(uuid: UUID): DocumentResponse {
        val document = documentRepository.findByUuid(uuid)
            .orElseThrow { WikiException(ErrorCode.DOCUMENT_NOT_FOUND) }
        return mapToResponse(document);
    }

    fun put(uuid: UUID, request: DocumentUpdateRequest): DocumentResponse {
        val (title, contents, writer, documentBytes) = request

        val document = documentRepository.findByUuid(uuid)
            .orElseThrow { WikiException(ErrorCode.DOCUMENT_NOT_FOUND) }

        val updateData = document.update(title, contents, writer, documentBytes, LocalDateTime.now())

        logService.save(updateData)
        return mapToResponse(document);
    }

    fun deleteById(id: Long) {
        documentRepository.findById(id)
            .orElseThrow { WikiException(ErrorCode.DOCUMENT_NOT_FOUND) }
        documentRepository.deleteById(id)
    }

    fun flushViews(views: Map<UUID, Int>) {
        val documents = documentRepository.findAllByUuidIn(views.keys)

        for (document in documents) {
            val countToAdd = views[document.uuid] ?: continue
            document.viewCount += countToAdd
        }

        documentRepository.saveAll(documents)
    }

    private fun mapToResponse(crewDocument: CrewDocument): DocumentResponse {
        val latestVersion = logService.findLatestVersionByDocument(crewDocument);
        val organizationDocumentResponses =
            organizationDocumentLinkService.findOrganizationDocumentResponsesByDocument(crewDocument)

        return DocumentResponse(
            crewDocument.id ?: throw WikiException(ErrorCode.DOCUMENT_NOT_FOUND),
            crewDocument.uuid,
            crewDocument.title,
            crewDocument.contents,
            crewDocument.writer,
            crewDocument.generateTime,
            crewDocument.viewCount,
            latestVersion,
            organizationDocumentResponses
        )
    }
}
