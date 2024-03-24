package com.wooteco.wiki.dto;

import java.time.LocalDateTime;

public record LogDetailResponse(Long logId, String title, String contents, String writer, LocalDateTime generateTime) {
}
