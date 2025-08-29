package com.wooteco.wiki.organizationdocument.repository;

import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationDocumentRepository extends JpaRepository<OrganizationDocument, Long> {

    Optional<OrganizationDocument> findByUuid(UUID uuid);
}
