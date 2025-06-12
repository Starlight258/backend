package com.wooteco.wiki.document.repository;

import com.wooteco.wiki.document.domain.Document;
import com.wooteco.wiki.document.fixture.DocumentFixture;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Nested
    @DisplayName("문서 제목으로 uuid를 조회하는 기능")
    class findUUidByTitle {

        @DisplayName("존재하지 않는 문서 제목으로 조회했을 때 Optional.empty를 반환한다")
        @Test
        void findUUidByTitle_success_byNonExistsDocument() {

            // when
            Optional<@NotNull UUID> actual = documentRepository.findUUidByTitle("nonExistsDocumentTitle");

            // then
            Assertions.assertThat(actual).isEmpty();
        }

        @DisplayName("존재하는 문서 제목으로 조회했을 때 Optional(UUID)를 반환한다")
        @Test
        void findUUidByTitle_success_byExistsDocument() {
            // given
            Document savedDocument = documentRepository.save(DocumentFixture.createDefault());

            // when
            Optional<@NotNull UUID> actual = documentRepository.findUUidByTitle(savedDocument.getTitle());

            // then
            Assertions.assertThat(actual.get()).isEqualTo(savedDocument.getUuid());
        }
    }

    @Nested
    @DisplayName("문서 전체 조회 기능")
    class findAll {

        @DisplayName("문서가 여러개 존재했을 때 List 형태로 반환한다")
        @Test
        void findAll_success_bySomeData() {
            // given
            documentRepository.save(DocumentFixture.create("title1", "content1", "writer1", 10L, LocalDateTime.now(), UUID.randomUUID(), 10L));
            documentRepository.save(DocumentFixture.create("title2", "content2", "writer2", 11L, LocalDateTime.now(), UUID.randomUUID(), 11L));

            // when
            List<@NotNull Document> documents = documentRepository.findAll();

            // then
            Assertions.assertThat(documents).hasSize(2);
        }

        @DisplayName("저장된 문서가 없을 때 빈 List를 반환한다")
        @Test
        void findAll_success_byNoData() {
            // when
            List<@NotNull Document> documents = documentRepository.findAll();

            // then
            Assertions.assertThat(documents).hasSize(0);
        }
    }
}
