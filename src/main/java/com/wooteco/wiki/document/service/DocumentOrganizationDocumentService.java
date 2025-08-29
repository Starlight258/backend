package com.wooteco.wiki.document.service;

import com.wooteco.wiki.document.domain.Document;
import com.wooteco.wiki.document.dto.DocumentOrganizationDocumentCreateRequest;
import com.wooteco.wiki.document.repository.DocumentRepository;
import com.wooteco.wiki.global.exception.ErrorCode;
import com.wooteco.wiki.global.exception.WikiException;
import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import com.wooteco.wiki.organizationdocument.dto.OrganizationDocumentTitleAndUuidResponse;
import com.wooteco.wiki.organizationdocument.dto.response.OrganizationDocumentResponse;
import com.wooteco.wiki.organizationdocument.repository.OrganizationDocumentRepository;
import com.wooteco.wiki.organizationdocument.service.DocumentOrganizationDocumentLinkService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DocumentOrganizationDocumentService {

    private final DocumentOrganizationDocumentLinkService documentOrganizationDocumentLinkService;
    private final DocumentRepository documentRepository;
    private final OrganizationDocumentRepository organizationDocumentRepository;

    public List<OrganizationDocumentTitleAndUuidResponse> readOrganizationTitleAndUuid(UUID documentUuid) {
        Document document = getDocument(documentUuid);
        List<OrganizationDocumentResponse> organizationDocumentResponsesByDocument = documentOrganizationDocumentLinkService.findOrganizationDocumentResponsesByDocument(
                document);

        return toOrganizationDocumentTitleAndUuidResponses(organizationDocumentResponsesByDocument);
    }

    public void addOrganizationDocument(UUID documentUuid, DocumentOrganizationDocumentCreateRequest documentOrganizationDocumentCreateRequest) {
        Document document = getDocument(documentUuid);
        OrganizationDocument organizationDocument = organizationDocumentRepository.save(documentOrganizationDocumentCreateRequest.toOrganizationDocument());

        documentOrganizationDocumentLinkService.link(document, organizationDocument);
    }

    public void deleteOrganizationDocument(UUID documentUuid, UUID organizationDocumentUuid) {
        Document document = getDocument(documentUuid);
        OrganizationDocument organizationDocument = getOrganizationDocument(organizationDocumentUuid);

        documentOrganizationDocumentLinkService.unlink(document, organizationDocument);
    }

    private List<OrganizationDocumentTitleAndUuidResponse> toOrganizationDocumentTitleAndUuidResponses(
            List<OrganizationDocumentResponse> organizationDocumentResponsesByDocument) {
        return organizationDocumentResponsesByDocument.stream()
                .map(OrganizationDocumentResponse::toOrganizationDocumentTitleAndUuidResponse)
                .collect(Collectors.toList());
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
