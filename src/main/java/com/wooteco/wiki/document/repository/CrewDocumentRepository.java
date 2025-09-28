package com.wooteco.wiki.document.repository;

import com.wooteco.wiki.document.domain.CrewDocument;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewDocumentRepository extends JpaRepository<CrewDocument, Long> {

    Optional<CrewDocument> findByUuid(UUID uuid);
}
