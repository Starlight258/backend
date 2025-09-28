package com.wooteco.wiki.log.repository;

import com.wooteco.wiki.log.domain.Log;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LogRepository extends JpaRepository<Log, Long> {

    @Query("SELECT l FROM Log l WHERE l.crewDocument.id = :documentId")
    Page<Log> findAllByDocumentId(Long documentId, Pageable pageable);

    @Query("SELECT MAX(l.version) FROM Log l WHERE l.crewDocument.id = :documentId")
    Optional<Long> findMaxVersionByDocumentId(@Param("documentId") Long documentId);
}

