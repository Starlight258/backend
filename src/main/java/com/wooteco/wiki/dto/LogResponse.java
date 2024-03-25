package com.wooteco.wiki.dto;

import java.time.LocalDateTime;

public record LogResponse(String title, LocalDateTime generateTime) {
}
