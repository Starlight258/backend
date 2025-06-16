package com.wooteco.wiki.admin.service;

import com.wooteco.wiki.document.service.DocumentService;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final DocumentService documentService;

    public AdminService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public void deleteDocumentByDocumentId(Long documentId) {
        documentService.deleteById(documentId);
    }
}
