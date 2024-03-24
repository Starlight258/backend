package com.wooteco.wiki.dto;

import com.wooteco.wiki.entity.Document;

public record LogResponse2(Long id) {
    public static LogResponse2 of(Document document) {
        return new LogResponse2(document.getDocumentId());
    }
}
