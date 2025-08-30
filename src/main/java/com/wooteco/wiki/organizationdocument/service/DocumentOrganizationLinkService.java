package com.wooteco.wiki.organizationdocument.service;

import com.wooteco.wiki.document.domain.Document;
import com.wooteco.wiki.organizationdocument.domain.DocumentOrganizationLink;
import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import com.wooteco.wiki.organizationdocument.dto.response.OrganizationDocumentResponse;
import com.wooteco.wiki.organizationdocument.repository.DocumentOrganizationLinkRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class DocumentOrganizationLinkService {

    private final DocumentOrganizationLinkRepository documentOrgDocLinkRepository;

    public void link(Document document, OrganizationDocument organizationDocument) {
        DocumentOrganizationLink documentOrgDocLink = new DocumentOrganizationLink(document,
                organizationDocument);
        documentOrgDocLinkRepository.save(documentOrgDocLink);
    }

    public void unlink(Document document, OrganizationDocument organizationDocument) {
        documentOrgDocLinkRepository.deleteByDocumentAndOrganizationDocument(document, organizationDocument);
    }

    public List<OrganizationDocumentResponse> findOrganizationDocumentResponsesByDocument(Document document) {
        List<DocumentOrganizationLink> documentOrganizationLinks = documentOrgDocLinkRepository.findAllByDocument(document);
        List<OrganizationDocument> organizationDocuments = documentOrganizationLinks.stream()
                .map(DocumentOrganizationLink::getOrganizationDocument)
                .toList();

        return organizationDocuments.stream()
                .map(OrganizationDocumentResponse::new)
                .toList();
    }
}
