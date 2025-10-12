package com.wooteco.wiki.organizationevent.repository;

import com.wooteco.wiki.organizationevent.domain.OrganizationEvent;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationEventRepository extends JpaRepository<OrganizationEvent, Long> {

    Optional<OrganizationEvent> findByUuid(UUID uuid);
}
