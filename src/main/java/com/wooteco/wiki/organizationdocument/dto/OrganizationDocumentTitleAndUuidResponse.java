package com.wooteco.wiki.organizationdocument.dto;

import java.util.UUID;

public record OrganizationDocumentTitleAndUuidResponse(
        UUID uuid,
        String title
) {
}
