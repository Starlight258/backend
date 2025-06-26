package com.wooteco.wiki.log.service;

import com.wooteco.wiki.document.domain.Document;
import com.wooteco.wiki.document.exception.DocumentBadRequestException;
import com.wooteco.wiki.document.fixture.DocumentFixture;
import com.wooteco.wiki.document.repository.DocumentRepository;
import com.wooteco.wiki.global.common.PageRequestDto;
import com.wooteco.wiki.log.domain.dto.LogResponse;
import com.wooteco.wiki.log.fixture.LogFixture;
import com.wooteco.wiki.log.repository.LogRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class LogServiceTest {

    @Autowired
    private LogService logService;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private LogRepository logRepository;
    
    @Nested
    @DisplayName("documentUuid로 요청 시 로그 리스트 반환하는 기능")
    class findAllByDocumentUuid {

        private PageRequestDto pageRequestDto = new PageRequestDto();
        private UUID documentUuid;
        private Document savedDocument;

        @BeforeEach
        void setUp() {
            savedDocument = documentRepository.save(DocumentFixture.create("title", "content", "writer", 100L, LocalDateTime.now(), UUID.randomUUID(), null));
            documentUuid = savedDocument.getUuid();

            logRepository.save(LogFixture.create("t1", "c1", "w1", 10L, LocalDateTime.now(), savedDocument));
            logRepository.save(LogFixture.create("t2", "c2", "w2", 20L, LocalDateTime.now(), savedDocument));
        }

        @DisplayName("documentUuid에 해당하는 로그들이 반환된다")
        @Test
        void findAllByDocumentUuid_success_bySomeData() {
            // when
            Page<LogResponse> actual = logService.findAllByDocumentUuid(documentUuid, pageRequestDto);

            // then
            SoftAssertions assertions = new SoftAssertions();
            assertions.assertThat(actual.getContent()).hasSize(2);
            assertions.assertThat(actual.getContent())
                    .extracting(LogResponse::title)
                    .containsExactly("t1", "t2");
        }

        @DisplayName("버전 번호가 순차적으로 부여된다")
        @Test
        void findAllByDocumentUuid_versionsAreNumberedCorrectly() {
            // when
            Page<LogResponse> actual = logService.findAllByDocumentUuid(documentUuid, pageRequestDto);

            List<Long> versions = actual.getContent().stream()
                    .map(LogResponse::version)
                    .toList();

            // then
            Assertions.assertThat(versions).containsExactly(1L, 2L);
        }

        @DisplayName("존재하지 않는 documentUuid로 요청 시 예외가 발생한다 : DocumentBadRequestException")
        @Test
        void findAllByDocumentUuid_throwsException_byNonExistsDocumentUuid() {
            // given
            UUID invalidUuid = UUID.randomUUID();

            // when & then
            Assertions.assertThatThrownBy(
                    () -> logService.findAllByDocumentUuid(invalidUuid, pageRequestDto)
            ).isInstanceOf(DocumentBadRequestException.class);
        }
    }
}
