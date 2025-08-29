package com.wooteco.wiki.organizationevent.fixture;

import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import com.wooteco.wiki.organizationevent.domain.OrganizationEvent;
import java.time.LocalDate;

public class OrganizationEventFixture {

    public static OrganizationEvent createDefault(OrganizationDocument organizationDocument) {
        return OrganizationEvent.create("defaultTitle", "defaultContent", "defaultWriter",
                LocalDate.now(), organizationDocument);
    }

}
