package com.wooteco.wiki.log.repository;

import com.wooteco.wiki.log.domain.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LogRepository extends JpaRepository<Log, Long> {

        @Query("SELECT l FROM Log l WHERE l.document.id = :documentId")
        Page<Log> findAllByDocumentId(Long documentId, Pageable pageable);
}
