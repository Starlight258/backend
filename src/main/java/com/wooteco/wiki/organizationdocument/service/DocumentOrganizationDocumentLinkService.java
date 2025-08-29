package com.wooteco.wiki.organizationdocument.service;

import com.wooteco.wiki.document.domain.Document;
import com.wooteco.wiki.document.repository.DocumentRepository;
import com.wooteco.wiki.global.exception.ErrorCode;
import com.wooteco.wiki.global.exception.WikiException;
import com.wooteco.wiki.organizationdocument.domain.DocumentOrganizationDocumentLink;
import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import com.wooteco.wiki.organizationdocument.dto.request.OrganizationDocumentCreateRequest;
import com.wooteco.wiki.organizationdocument.repository.DocumentOrganizationDocumentLinkRepository;
import com.wooteco.wiki.organizationdocument.repository.OrganizationDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class DocumentOrganizationDocumentLinkService {

    private final DocumentOrganizationDocumentLinkRepository documentOrgDocLinkRepository;
    private final OrganizationDocumentRepository organizationDocumentRepository;
    private final DocumentRepository documentRepository;


    public void createOrganizationDocument(OrganizationDocumentCreateRequest organizationDocumentCreateRequest) {
        Document document = documentRepository.findByUuid(organizationDocumentCreateRequest.documentUUID())
                .orElseThrow(() -> new WikiException(ErrorCode.DOCUMENT_NOT_FOUND));

        OrganizationDocument organizationDocument = organizationDocumentCreateRequest.toOrganizationDocument();
        OrganizationDocument savedOrganizationDocument = organizationDocumentRepository.save(organizationDocument);

        DocumentOrganizationDocumentLink documentOrgDocLink = new DocumentOrganizationDocumentLink(document,
                savedOrganizationDocument);
        documentOrgDocLinkRepository.save(documentOrgDocLink);
    }
}
