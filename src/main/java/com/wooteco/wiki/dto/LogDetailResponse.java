package com.wooteco.wiki.dto;

import com.wooteco.wiki.entity.Document;
import java.time.LocalDateTime;

public record LogDetailResponse(Long logId, String title, String contents, String writer, LocalDateTime generateTime) {
    public static LogDetailResponse of(Document document) {
        return new LogDetailResponse(document.getDocumentId(), document.getTitle(), document.getContents(),
                document.getWriter(), document.getGenerateTime());
    }
}
