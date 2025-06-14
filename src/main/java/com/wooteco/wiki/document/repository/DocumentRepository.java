package com.wooteco.wiki.document.repository;

import com.wooteco.wiki.document.domain.Document;
import com.wooteco.wiki.document.domain.Title;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    Optional<Document> findByTitle(String title);

    Boolean existsByTitle(String title); // TODO 2025. 6. 14. 15:34: 제거 가능 한 지 테스트 하고 제거

    List<Document> findAllByOrderByGenerateTimeDesc();

    List<Title> findAllByTitleStartingWith(String keyWord);

    Optional<Document> findByUuid(UUID uuid); // TODO 2025. 6. 14. 15:34: 제거 가능 한 지 테스트 하고 제거

    @Query("SELECT d.uuid FROM Document d WHERE d.title = :title")
    Optional<UUID> findUUidByTitle(@Param("title")String title);
}
