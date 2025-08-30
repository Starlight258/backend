package com.wooteco.wiki.organizationdocument.fixture;

import com.wooteco.wiki.document.dto.DocumentOrganizationMappingAddRequest;
import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
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

    public static DocumentOrganizationMappingAddRequest createDocumentOrganizationDocumentCreateRequest(String title, String contents, String writer, Long documentBytes, UUID uuid) {
        return new DocumentOrganizationMappingAddRequest(title, contents, writer, documentBytes, uuid);
    }

    public static DocumentOrganizationMappingAddRequest createDocumentOrganizationDocumentCreateRequestDefault() {
        return createDocumentOrganizationDocumentCreateRequest("defaultTitle", "defaultContents", "defaultWriter", 10L,
                UUID.randomUUID());
    }
}
