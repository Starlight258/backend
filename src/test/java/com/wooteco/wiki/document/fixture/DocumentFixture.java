package com.wooteco.wiki.document.fixture;

import com.wooteco.wiki.document.domain.CrewDocument;
import com.wooteco.wiki.document.domain.dto.DocumentCreateRequest;
import com.wooteco.wiki.document.domain.dto.DocumentUpdateRequest;
import java.util.UUID;

public class DocumentFixture {

    public static CrewDocument createCrewDocument(String title, String content, String writer, Long documentBytes,
                                                  UUID uuid) {
        return new CrewDocument(title, content, writer, documentBytes, uuid);
    }

    public static CrewDocument createDefaultCrewDocument() {
        return createCrewDocument("defaultCrewTitle", "defaultContent", "defaultWriter", 10L, UUID.randomUUID());
    }

    public static DocumentCreateRequest createDocumentCreateRequest(String title, String contents, String writer,
                                                                    Long documentBytes, UUID uuid) {
        return new DocumentCreateRequest(title, contents, writer, documentBytes, uuid);
    }

    public static DocumentCreateRequest createDocumentCreateRequestDefault() {
        return createDocumentCreateRequest("defaultTitle", "defaultContent", "defaultWriter", 10L, UUID.randomUUID());
    }

    public static DocumentUpdateRequest createDocumentUpdateRequest(String title, String contents, String writer,
                                                                    Long documentBytes) {
        return new DocumentUpdateRequest(title, contents, writer, documentBytes, UUID.randomUUID());
    }
}
