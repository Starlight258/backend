package com.wooteco.wiki.dto;

import com.wooteco.wiki.entity.Document;
import java.util.List;

public record LogFindAllResponse(List<LogResponse2> logs) {
    public static LogFindAllResponse of(List<Document> documents) {
        List<LogResponse2> logs = documents.stream()
                .map(LogResponse2::of)
                .toList();
        return new LogFindAllResponse(logs);
    }
}
