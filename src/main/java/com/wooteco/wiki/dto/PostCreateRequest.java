package com.wooteco.wiki.dto;

import lombok.NonNull;

public record PostCreateRequest(@NonNull String contents, @NonNull String writer) {
}
