package com.wooteco.wiki.log.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record LogDetailResponse(Long logId, UUID uuid, String title, String contents, String writer, LocalDateTime generateTime) {
}
