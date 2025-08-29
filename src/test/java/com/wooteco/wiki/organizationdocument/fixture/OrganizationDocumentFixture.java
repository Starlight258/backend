package com.wooteco.wiki.organizationdocument.fixture;

import com.wooteco.wiki.document.dto.DocumentOrganizationDocumentCreateRequest;
import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import com.wooteco.wiki.organizationdocument.dto.request.DocumentOrganizationDocumentLinkCreateRequest;
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

    public static DocumentOrganizationDocumentLinkCreateRequest createOrganizationDocumentCreateRequest(
            String title,
            String contents,
            String writer,
            Long documentBytes,
            UUID uuid,
            UUID documentUUID
    ) {
        return new DocumentOrganizationDocumentLinkCreateRequest(title, contents, writer, documentBytes, uuid, documentUUID);
    }

    public static DocumentOrganizationDocumentLinkCreateRequest createOrganizationDocumentCreateRequestDefault(UUID documentUUID) {
        return createOrganizationDocumentCreateRequest("defaultTitle", "defaultContent", "defaultWriter", 10L,
                UUID.randomUUID(), documentUUID);
    }

    public static DocumentOrganizationDocumentLinkCreateRequest createOrganizationUpdateRequest(String title, String contents,
                                                                                                String writer, Long documentBytes) {
        return new DocumentOrganizationDocumentLinkCreateRequest(title, contents, writer, documentBytes, UUID.randomUUID(),
                UUID.randomUUID());
    }

    public static DocumentOrganizationDocumentCreateRequest createDocumentOrganizationDocumentCreateRequest(String title, String contents, String writer, Long documentBytes, UUID uuid) {
        return new DocumentOrganizationDocumentCreateRequest(title, contents, writer, documentBytes, uuid);
    }

    public static DocumentOrganizationDocumentCreateRequest createDocumentOrganizationDocumentCreateRequestDefault() {
        return createDocumentOrganizationDocumentCreateRequest("defaultTitle", "defaultContents", "defaultWriter", 10L,
                UUID.randomUUID());
    }
}
