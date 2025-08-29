package com.wooteco.wiki.organizationevent.dto.request;

import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import com.wooteco.wiki.organizationevent.domain.OrganizationEvent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record OrganizationEventCreateRequest(
        @NotBlank String title,
        String contents,
        @NotBlank String writer,
        @NotNull LocalDate occurredAt,
        @NotNull UUID organizationDocumentUUID
) {
    public OrganizationEvent toDomain(final OrganizationDocument organizationDocument) {
        return OrganizationEvent.create(title, contents, writer, occurredAt, organizationDocument);
    }
}
