package com.wooteco.wiki.document.service;

import com.wooteco.wiki.document.domain.CrewDocument;
import com.wooteco.wiki.document.dto.DocumentOrganizationMappingAddRequest;
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

    public List<OrganizationDocumentSearchResponse> searchOrganizationDocument(UUID documentUuid) {
        CrewDocument crewDocument = getDocument(documentUuid);
        List<OrganizationDocumentResponse> organizationDocumentResponsesByDocument = documentOrganizationLinkService.findOrganizationDocumentResponsesByDocument(
                crewDocument);

        return toOrganizationDocumentTitleAndUuidResponses(organizationDocumentResponsesByDocument);
    }

    public void addOrganizationDocument(UUID documentUuid, DocumentOrganizationMappingAddRequest documentOrganizationMappingAddRequest) {
        CrewDocument crewDocument = getDocument(documentUuid);
        OrganizationDocument organizationDocument = organizationDocumentRepository.save(
                documentOrganizationMappingAddRequest.toOrganizationDocument());

        documentOrganizationLinkService.link(crewDocument, organizationDocument);
    }

    public void deleteOrganizationDocument(UUID documentUuid, UUID organizationDocumentUuid) {
        CrewDocument crewDocument = getDocument(documentUuid);
        OrganizationDocument organizationDocument = getOrganizationDocument(organizationDocumentUuid);

        documentOrganizationLinkService.unlink(crewDocument, organizationDocument);
    }

    private List<OrganizationDocumentSearchResponse> toOrganizationDocumentTitleAndUuidResponses(
            List<OrganizationDocumentResponse> organizationDocumentResponsesByDocument) {
        return organizationDocumentResponsesByDocument.stream()
                .map(OrganizationDocumentResponse::toOrganizationDocumentTitleAndUuidResponse)
                .toList();
    }

    private CrewDocument getDocument(UUID uuid) {
        return documentRepository.findByUuid(uuid)
                .orElseThrow(() -> new WikiException(ErrorCode.DOCUMENT_NOT_FOUND));
    }

    private OrganizationDocument getOrganizationDocument(UUID uuid) {
        return organizationDocumentRepository.findByUuid(uuid)
                .orElseThrow(() -> new WikiException(ErrorCode.ORGANIZATION_DOCUMENT_NOT_FOUND));
    }
}
