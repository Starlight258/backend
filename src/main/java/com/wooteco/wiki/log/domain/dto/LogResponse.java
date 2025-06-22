package com.wooteco.wiki.log.domain.dto;

import com.wooteco.wiki.log.domain.Log;
import java.time.LocalDateTime;

public record LogResponse(Long id, Long version, String writer, Long documentBytes, LocalDateTime generateTime) {

    public static LogResponse of(Log log, Long version) {
        return new LogResponse(
                log.getId(),
                version,
                log.getWriter(),
                log.getDocumentBytes(),
                log.getGenerateTime()
        );
    }
}
