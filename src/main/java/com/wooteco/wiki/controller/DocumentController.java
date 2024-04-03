package com.wooteco.wiki.controller;

import com.wooteco.wiki.dto.*;
import com.wooteco.wiki.service.DocumentService;
import com.wooteco.wiki.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final LogService logService;

    @PostMapping("")
    public ResponseEntity<DocumentResponse> post(@RequestBody DocumentCreateRequest documentCreateRequest) {
        DocumentResponse response = documentService.post(documentCreateRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<?> getRandom() {
        Optional<DocumentResponse> response = documentService.getRandom();
        if (response.isEmpty()) {
            return notFound();
        }
        return ResponseEntity.ok(response.get());
    }

    @GetMapping("/{title}")
    public ResponseEntity<?> get(@PathVariable String title) {
        Optional<DocumentResponse> response = documentService.get(title);
        if (response.isEmpty()) {
            return notFound();
        }
        return ResponseEntity.ok(response.get());
    }

    @GetMapping("/{title}/log")
    public ResponseEntity<?> getLogs(@PathVariable String title) {
        return ResponseEntity.ok(logService.getLogs(title));
    }

    @GetMapping("/log/{logId}")
    public ResponseEntity<LogDetailResponse> getDocumentLogs(@PathVariable Long logId) {
        LogDetailResponse logDetail = logService.getLogDetail(logId);
        return ResponseEntity.ok(logDetail);
    }

    @PutMapping("/{title}")
    public ResponseEntity<DocumentResponse> put(@PathVariable String title,
                                                @RequestBody DocumentUpdateRequest documentUpdateRequest) {
        DocumentResponse response = documentService.put(title, documentUpdateRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recent")
    public ResponseEntity<DocumentFindAllByRecentResponse> getRecentDocuments() {
        DocumentFindAllByRecentResponse response = documentService.getRecentDocuments();
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<ErrorResponse> notFound() {
        return ResponseEntity.status(NOT_FOUND)
                .body(new ErrorResponse("없는 문서입니다."));
    }
}
