package com.wooteco.wiki.organizationevent.dto.response;

import com.wooteco.wiki.organizationevent.domain.OrganizationEvent;
import java.util.UUID;

public record OrganizationEventCreateResponse(
        UUID organizationEventUUID
) {
    public static OrganizationEventCreateResponse from(OrganizationEvent organizationEvent) {
        return new OrganizationEventCreateResponse(organizationEvent.getUuid());
    }
}
