package com.wooteco.wiki.document.fixture;

import com.wooteco.wiki.document.domain.Document;
import com.wooteco.wiki.document.domain.dto.DocumentCreateRequest;
import com.wooteco.wiki.document.domain.dto.DocumentUpdateRequest;
import java.time.LocalDateTime;
import java.util.UUID;

public class DocumentFixture {

    public static Document create(String title, String content, String writer, Long documentBytes,
                                  LocalDateTime dateTime, UUID uuid) {
        return new Document(null, title, content, writer, documentBytes, dateTime, uuid);
    }

    public static Document createDefault() {
        return create("defaultTitle", "defaultContent", "defaultWriter", 10L, LocalDateTime.now(), UUID.randomUUID());
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
