package com.wooteco.wiki.dto;

import com.wooteco.wiki.entity.Document;
import java.util.List;

public record LogFindAllResponse(List<LogDetailResponse> logs) {
    public static LogFindAllResponse of(List<Document> documents) {
        List<LogDetailResponse> logs = documents.stream()
                .map(LogDetailResponse::of)
                .toList();
        return new LogFindAllResponse(logs);
    }
}
