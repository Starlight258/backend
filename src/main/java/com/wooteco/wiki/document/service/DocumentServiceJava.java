package com.wooteco.wiki.document.service;

import com.wooteco.wiki.document.domain.Document;
import com.wooteco.wiki.document.dto.DocumentOrganizationAddRequest;
import com.wooteco.wiki.document.repository.DocumentRepository;
import com.wooteco.wiki.global.exception.ErrorCode;
import com.wooteco.wiki.global.exception.WikiException;
import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import com.wooteco.wiki.organizationdocument.dto.OrganizationDocumentSearchResponse;
import com.wooteco.wiki.organizationdocument.dto.response.OrganizationDocumentResponse;
import com.wooteco.wiki.organizationdocument.repository.OrganizationDocumentRepository;
import com.wooteco.wiki.organizationdocument.service.DocumentOrganizationLinkService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DocumentServiceJava {

    private final DocumentOrganizationLinkService documentOrganizationLinkService;
    private final DocumentRepository documentRepository;
    private final OrganizationDocumentRepository organizationDocumentRepository;

    public List<OrganizationDocumentSearchResponse> readOrganizationTitleAndUuid(UUID documentUuid) {
        Document document = getDocument(documentUuid);
        List<OrganizationDocumentResponse> organizationDocumentResponsesByDocument = documentOrganizationLinkService.findOrganizationDocumentResponsesByDocument(
                document);

        return toOrganizationDocumentTitleAndUuidResponses(organizationDocumentResponsesByDocument);
    }

    public void addOrganizationDocument(UUID documentUuid, DocumentOrganizationAddRequest documentOrganizationAddRequest) {
        Document document = getDocument(documentUuid);
        OrganizationDocument organizationDocument = organizationDocumentRepository.save(documentOrganizationAddRequest.toOrganizationDocument());

        documentOrganizationLinkService.link(document, organizationDocument);
    }

    public void deleteOrganizationDocument(UUID documentUuid, UUID organizationDocumentUuid) {
        Document document = getDocument(documentUuid);
        OrganizationDocument organizationDocument = getOrganizationDocument(organizationDocumentUuid);

        documentOrganizationLinkService.unlink(document, organizationDocument);
    }

    private List<OrganizationDocumentSearchResponse> toOrganizationDocumentTitleAndUuidResponses(
            List<OrganizationDocumentResponse> organizationDocumentResponsesByDocument) {
        return organizationDocumentResponsesByDocument.stream()
                .map(OrganizationDocumentResponse::toOrganizationDocumentTitleAndUuidResponse)
                .toList();
    }

    private Document getDocument(UUID uuid) {
        return documentRepository.findByUuid(uuid)
                .orElseThrow(() -> new WikiException(ErrorCode.DOCUMENT_NOT_FOUND));
    }

    private OrganizationDocument getOrganizationDocument(UUID uuid) {
        return organizationDocumentRepository.findByUuid(uuid)
                .orElseThrow(() -> new WikiException(ErrorCode.ORGANIZATION_DOCUMENT_NOT_FOUND));
    }
}
