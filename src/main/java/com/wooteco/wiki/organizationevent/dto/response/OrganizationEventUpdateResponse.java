package com.wooteco.wiki.organizationevent.dto.response;

import com.wooteco.wiki.organizationevent.domain.OrganizationEvent;
import java.time.LocalDate;
import java.util.UUID;

public record OrganizationEventUpdateResponse(
        UUID organizationEventUUID,
        String title,
        String contents,
        String writer,
        LocalDate occurredAt
) {
    public static OrganizationEventUpdateResponse from(OrganizationEvent organizationEvent) {
        return new OrganizationEventUpdateResponse(
                organizationEvent.getUuid(),
                organizationEvent.getTitle(),
                organizationEvent.getContents(),
                organizationEvent.getWriter(),
                organizationEvent.getOccurredAt()
        );
    }
}
