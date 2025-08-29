package com.wooteco.wiki.organizationevent.service;

import static com.wooteco.wiki.global.exception.ErrorCode.ORGANIZATION_DOCUMENT_NOT_FOUND;
import static com.wooteco.wiki.global.exception.ErrorCode.ORGANIZATION_EVENT_NOT_FOUND;

import com.wooteco.wiki.global.exception.WikiException;
import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import com.wooteco.wiki.organizationdocument.dto.request.OrganizationDocumentUpdateRequest;
import com.wooteco.wiki.organizationdocument.dto.response.OrganizationDocumentResponse;
import com.wooteco.wiki.organizationdocument.repository.OrganizationDocumentRepository;
import com.wooteco.wiki.organizationevent.domain.OrganizationEvent;
import com.wooteco.wiki.organizationevent.dto.request.OrganizationEventCreateRequest;
import com.wooteco.wiki.organizationevent.dto.request.OrganizationEventUpdateRequest;
import com.wooteco.wiki.organizationevent.dto.response.OrganizationEventCreateResponse;
import com.wooteco.wiki.organizationevent.dto.response.OrganizationEventUpdateResponse;
import com.wooteco.wiki.organizationevent.repository.OrganizationEventRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class OrganizationEventService {

    private final OrganizationEventRepository organizationEventRepository;
    private final OrganizationDocumentRepository organizationDocumentRepository;

    public OrganizationEventCreateResponse post(final OrganizationEventCreateRequest request) {
        OrganizationDocument organizationDocument = getOrganizationDocument(request);
        OrganizationEvent event = request.toDomain(organizationDocument);
        OrganizationEvent savedEvent = organizationEventRepository.save(event);
        return OrganizationEventCreateResponse.from(savedEvent);
    }

    public OrganizationEventUpdateResponse put(final UUID organizationEventUUID,
                                               final OrganizationEventUpdateRequest request) {
        OrganizationEvent organizationEvent = getOrganizationEvent(organizationEventUUID);
        organizationEvent.update(
                request.title(),
                request.contents(),
                request.writer(),
                request.occurredAt()
        );
        return OrganizationEventUpdateResponse.from(organizationEvent);
    }

    public void delete(final UUID organizationEventUUID) {
        getOrganizationEvent(organizationEventUUID);

        organizationEventRepository.deleteByUuid(organizationEventUUID);
    }

    private OrganizationDocument getOrganizationDocument(
            final OrganizationEventCreateRequest organizationEventCreateRequest) {
        return organizationDocumentRepository.findByUuid(
                        organizationEventCreateRequest.organizationDocumentUUID())
                .orElseThrow(() -> new WikiException(ORGANIZATION_DOCUMENT_NOT_FOUND));
    }

    private OrganizationEvent getOrganizationEvent(final UUID uuid) {
        return organizationEventRepository.findByUuid(uuid)
                .orElseThrow(() -> new WikiException(ORGANIZATION_EVENT_NOT_FOUND));

    }
}
