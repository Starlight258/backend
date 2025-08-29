package com.wooteco.wiki.document.dto;

import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import java.util.UUID;

public record DocumentOrganizationAddRequest(
        String title,
        String contents,
        String writer,
        Long documentBytes,
        UUID uuid
) {
    public OrganizationDocument toOrganizationDocument() {
        return new OrganizationDocument(
                title,
                contents,
                writer,
                documentBytes,
                uuid
        );
    }
}
