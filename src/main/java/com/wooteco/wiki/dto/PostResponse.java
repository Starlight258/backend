package com.wooteco.wiki.dto;

import java.time.LocalDateTime;

public record PostResponse(long postId, String title, String contents, String writer, LocalDateTime generateTime) {
}
