package com.wooteco.wiki.organizationevent.service;

import static com.wooteco.wiki.global.exception.ErrorCode.ORGANIZATION_DOCUMENT_NOT_FOUND;

import com.wooteco.wiki.global.exception.WikiException;
import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import com.wooteco.wiki.organizationdocument.dto.request.OrganizationDocumentUpdateRequest;
import com.wooteco.wiki.organizationdocument.dto.response.OrganizationDocumentResponse;
import com.wooteco.wiki.organizationdocument.repository.OrganizationDocumentRepository;
import com.wooteco.wiki.organizationevent.domain.OrganizationEvent;
import com.wooteco.wiki.organizationevent.dto.request.OrganizationEventCreateRequest;
import com.wooteco.wiki.organizationevent.dto.response.OrganizationEventCreateResponse;
import com.wooteco.wiki.organizationevent.repository.OrganizationEventRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class OrganizationEventService {

    private final OrganizationEventRepository organizationEventRepository;
    private final OrganizationDocumentRepository organizationDocumentRepository;

    public OrganizationDocumentResponse update(OrganizationDocumentUpdateRequest organizationDocumentUpdateRequest) {
        OrganizationDocument organizationDocument = organizationDocumentRepository.findByUuid(
                        organizationDocumentUpdateRequest.uuid())
                .orElseThrow(() -> new WikiException(ORGANIZATION_DOCUMENT_NOT_FOUND));

        organizationDocument.update(
                organizationDocumentUpdateRequest.title(),
                organizationDocumentUpdateRequest.contents(),
                organizationDocumentUpdateRequest.writer(),
                organizationDocumentUpdateRequest.documentBytes(),
                LocalDateTime.now()
        );
        return new OrganizationDocumentResponse(organizationDocument);
    }

    public OrganizationEventCreateResponse post(final OrganizationEventCreateRequest organizationEventCreateRequest) {
        OrganizationDocument organizationDocument = getOrganizationDocument(organizationEventCreateRequest);
        OrganizationEvent event = organizationEventCreateRequest.toDomain(organizationDocument);
        OrganizationEvent savedEvent = organizationEventRepository.save(event);
        return OrganizationEventCreateResponse.from(savedEvent);
    }

    private OrganizationDocument getOrganizationDocument(
            final OrganizationEventCreateRequest organizationEventCreateRequest) {
        return organizationDocumentRepository.findByUuid(
                        organizationEventCreateRequest.organizationDocumentUUID())
                .orElseThrow(() -> new WikiException(ORGANIZATION_DOCUMENT_NOT_FOUND));
    }
}
