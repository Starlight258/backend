package com.wooteco.wiki.document.controller

import com.wooteco.wiki.document.exception.*
import com.wooteco.wiki.document.service.DocumentSearchService
import com.wooteco.wiki.document.service.DocumentService
import com.wooteco.wiki.document.service.UUIDService
import com.wooteco.wiki.log.domain.dto.LogDetailResponse
import com.wooteco.wiki.log.domain.dto.LogResponse
import com.wooteco.wiki.log.service.LogService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/document")
class DocumentController(
    private val documentService: DocumentService,
    private val logService: LogService,
    private val documentSearchService: DocumentSearchService,
    private val uuidService: UUIDService,
) {

    @PostMapping("")
    fun post(@RequestBody documentCreateRequest: DocumentCreateRequest): ResponseEntity<DocumentResponse> {
        val response = documentService.post(documentCreateRequest)
        return ResponseEntity.ok(response)
    }

    @GetMapping("")
    fun getRandom(): ResponseEntity<DocumentResponse> {
        val response = documentService.getRandom()
        return ResponseEntity.ok(response)
    }

    @GetMapping("title/{title}")
    fun get(@PathVariable title: String): ResponseEntity<Any> {
        val response = documentService.get(title)
        return ResponseEntity.ok(response)
    }

    @GetMapping("uuid/{uuidText}")
    fun getByUuid(@PathVariable uuidText: String): ResponseEntity<Any> {
        val uuid = UUID.fromString(uuidText)
        val response = documentService.getByUuid(uuid)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{title}/log")
    fun getLogs(@PathVariable title: String): ResponseEntity<List<LogResponse>> {
        return ResponseEntity.ok(logService.getLogs(title))
    }

    @GetMapping("/log/{logId}")
    fun getDocumentLogs(@PathVariable logId: Long): ResponseEntity<LogDetailResponse> {
        val logDetail = logService.getLogDetail(logId)
        return ResponseEntity.ok(logDetail)
    }

    @PutMapping("/{uuidText}")
    fun put(
        @PathVariable uuidText: String,
        @RequestBody documentUpdateRequest: DocumentUpdateRequest
    ): ResponseEntity<DocumentResponse> {
        val response = documentService.put(uuidText, documentUpdateRequest)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/recent")
    fun getRecentDocuments(): ResponseEntity<DocumentFindAllByRecentResponse> {
        val response = documentService.getRecentDocuments()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/search")
    fun search(@RequestParam keyWord: String): List<String> {
        return documentSearchService.search(keyWord)
    }

    @GetMapping("/uuid")
    fun getUUID(): ResponseEntity<UUIDResponse> {
        val uuid = uuidService.generate();
        return ResponseEntity.ok(UUIDResponse(uuid.toString()))
    }
}
