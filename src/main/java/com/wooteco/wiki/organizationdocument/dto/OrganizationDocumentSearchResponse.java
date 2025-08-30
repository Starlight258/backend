package com.wooteco.wiki.organizationdocument.dto;

import java.util.UUID;

public record OrganizationDocumentSearchResponse(
        UUID uuid,
        String title
) {
}
