package com.wooteco.wiki.organizationdocument.fixture;

import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import com.wooteco.wiki.organizationdocument.dto.request.OrganizationDocumentCreateRequest;
import java.time.LocalDateTime;
import java.util.UUID;

public class OrganizationDocumentFixture {

    public static OrganizationDocument create(String title, String contents, String writer, Long documentBytes,
                                              UUID uuid, LocalDateTime dateTime) {
        return new OrganizationDocument(title, contents, writer, documentBytes, uuid, dateTime);
    }

    public static OrganizationDocument createDefault() {
        return create("defaultTitle", "defaultContent", "defaultWriter", 10L, UUID.randomUUID(), LocalDateTime.now());
    }

    public static OrganizationDocumentCreateRequest createOrganizationDocumentCreateRequest(
            String title,
            String contents,
            String writer,
            Long documentBytes,
            UUID uuid,
            UUID documentUuid
    ) {
        return new OrganizationDocumentCreateRequest(title, contents, writer, documentBytes, uuid, documentUuid);
    }

    public static OrganizationDocumentCreateRequest createOrganizationDocumentCreateRequestDefault(UUID documentUuid) {
        return createOrganizationDocumentCreateRequest("defaultTitle", "defaultContent", "defaultWriter", 10L,
                UUID.randomUUID(), documentUuid);
    }
}
