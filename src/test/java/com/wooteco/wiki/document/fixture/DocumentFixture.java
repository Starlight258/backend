package com.wooteco.wiki.document.fixture;

import com.wooteco.wiki.document.domain.Document;
import com.wooteco.wiki.document.domain.dto.DocumentCreateRequest;
import java.time.LocalDateTime;
import java.util.UUID;

public class DocumentFixture {

    public static Document create(String title, String content, String writer, Long documentBytes, LocalDateTime dateTime, UUID uuid, Long id) {
        return new Document(title, content, writer, documentBytes, dateTime, uuid, id);
    }

    public static Document createDefault() {
        return create("defaultTitle", "defaultContent", "defaultWriter", 10L, LocalDateTime.now(), UUID.randomUUID(), 1L);
    }

    public static DocumentCreateRequest createDocumentCreateRequest(String title, String contents, String writer, Long documentBytes) {
        return new DocumentCreateRequest(title, contents, writer, documentBytes);
    }

    public static DocumentCreateRequest createDocumentCreateRequestDefault() {
        return createDocumentCreateRequest("defaultTitle", "defaultContent", "defaultWriter", 10L);
    }
}
