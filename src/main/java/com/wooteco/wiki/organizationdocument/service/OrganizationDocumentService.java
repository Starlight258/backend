package com.wooteco.wiki.organizationdocument.service;

import static com.wooteco.wiki.global.exception.ErrorCode.ORGANIZATION_DOCUMENT_NOT_FOUND;

import com.wooteco.wiki.global.exception.WikiException;
import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import com.wooteco.wiki.organizationdocument.dto.request.OrganizationDocumentUpdateRequest;
import com.wooteco.wiki.organizationdocument.dto.response.OrganizationDocumentResponse;
import com.wooteco.wiki.organizationdocument.repository.OrganizationDocumentRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class OrganizationDocumentService {

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
}
