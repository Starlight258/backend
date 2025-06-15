package com.wooteco.wiki.document.service

import com.wooteco.wiki.document.domain.Document
import com.wooteco.wiki.document.domain.dto.DocumentCreateRequest
import com.wooteco.wiki.document.domain.dto.DocumentResponse
import com.wooteco.wiki.document.domain.dto.DocumentUpdateRequest
import com.wooteco.wiki.document.domain.dto.DocumentUuidResponse
import com.wooteco.wiki.document.exception.DocumentNotFoundException
import com.wooteco.wiki.document.exception.DuplicateDocumentException
import com.wooteco.wiki.document.repository.DocumentRepository
import com.wooteco.wiki.global.common.PageRequestDto
import com.wooteco.wiki.log.domain.Log
import com.wooteco.wiki.log.repository.LogRepository
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID
import kotlin.random.Random

@Service
@Transactional
class DocumentService(
    private val documentRepository: DocumentRepository,
    private val logRepository: LogRepository,
    private val random: Random,
) {

    fun post(request: DocumentCreateRequest): DocumentResponse {
        val (title, contents, writer, documentBytes, uuid) = request

        if (documentRepository.existsByTitle(title)) {
            throw DuplicateDocumentException("제목이 겹치는 문서가 있습니다.")
        }

        val document = Document(title, contents, writer, documentBytes, LocalDateTime.now(), uuid)
        val savedDocument = documentRepository.save(document)

        val log = Log(title, uuid, contents, writer, documentBytes, savedDocument.generateTime, savedDocument)
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

    fun findAll(requestDto: PageRequestDto): Page<Document> {
            val pageable = requestDto.toPageable()
            return documentRepository.findAll(pageable)
    }

    fun get(title: String): DocumentResponse =
        documentRepository.findByTitle(title)
            .map { mapToResponse(it) }
            .orElseThrow { DocumentNotFoundException("없는 문서입니다.") }

    fun getUuidByTitle(title: String): DocumentUuidResponse =
        documentRepository.findUuidByTitle(title)
            .map(::DocumentUuidResponse)
            .orElseThrow { DocumentNotFoundException("없는 문서입니다.") }

    fun getByUuid(uuid: UUID): DocumentResponse =
        documentRepository.findByUuid(uuid)
            .map { mapToResponse(it) }
            .orElseThrow { DocumentNotFoundException("없는 문서입니다.") }

    fun put(uuidText: String, request: DocumentUpdateRequest): DocumentResponse {
        val (title, contents, writer, documentBytes) = request

        val uuid = UUID.fromString(uuidText)
        val document = documentRepository.findByUuid(uuid)
            .orElseThrow { DocumentNotFoundException("존재하지 않는 UUID의 문서입니다.") }

        val updateData = document.update(title, contents, writer, documentBytes, LocalDateTime.now())

        val log = Log(
            updateData.title,
            uuid,
            updateData.contents,
            updateData.writer,
            updateData.documentBytes,
            updateData.generateTime,
            updateData
        )
        logRepository.save(log)

        return mapToResponse(document)
    }

    private fun mapToResponse(document: Document): DocumentResponse =
        DocumentResponse(
            document.id ?: throw DocumentNotFoundException("문서 ID가 없습니다."),
            document.uuid,
            document.title,
            document.contents,
            document.writer,
            document.generateTime
        )
}
