package com.wooteco.wiki.document.domain.dto;

import com.wooteco.wiki.document.domain.CrewDocument;
import java.util.UUID;

public record CrewDocumentCreateRequest(
        String title,
        String contents,
        String writer,
        Long documentBytes,
        UUID uuid
) {
    public CrewDocument toCrewDocument() {
        return new CrewDocument(title, contents, writer, documentBytes, uuid);
    }
}
