package com.wooteco.wiki.organizationdocument.service;

import com.wooteco.wiki.document.domain.Document;
import com.wooteco.wiki.organizationdocument.domain.DocumentOrganizationDocumentLink;
import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import com.wooteco.wiki.organizationdocument.dto.response.OrganizationDocumentResponse;
import com.wooteco.wiki.organizationdocument.repository.DocumentOrganizationDocumentLinkRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class DocumentOrganizationDocumentLinkService {

    private final DocumentOrganizationDocumentLinkRepository documentOrgDocLinkRepository;

    public void link(Document document, OrganizationDocument organizationDocument) {
        DocumentOrganizationDocumentLink documentOrgDocLink = new DocumentOrganizationDocumentLink(document,
                organizationDocument);
        documentOrgDocLinkRepository.save(documentOrgDocLink);
    }

    public void unlink(Document document, OrganizationDocument organizationDocument) {
        documentOrgDocLinkRepository.deleteByDocumentAndOrganizationDocument(document, organizationDocument);
    }

    public List<OrganizationDocumentResponse> findOrganizationDocumentResponsesByDocument(Document document) {
        List<DocumentOrganizationDocumentLink> documentOrganizationDocumentLinks = documentOrgDocLinkRepository.findAllByDocument(document);
        List<OrganizationDocument> organizationDocuments = documentOrganizationDocumentLinks.stream()
                .map(DocumentOrganizationDocumentLink::getOrganizationDocument)
                .toList();

        return organizationDocuments.stream()
                .map(OrganizationDocumentResponse::new)
                .toList();
    }
}
