package com.wooteco.wiki.organizationevent.repository;

import com.wooteco.wiki.organizationevent.domain.OrganizationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationEventRepository extends JpaRepository<OrganizationEvent, Long> {
}
