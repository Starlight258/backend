package com.wooteco.wiki.organizationevent.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record OrganizationEventUpdateRequest(
        @NotBlank String title,
        String contents,
        @NotBlank String writer,
        @NotNull LocalDate occurredAt
) {
}
