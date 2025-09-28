package com.wooteco.wiki.log.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wooteco.wiki.document.domain.CrewDocument;
import com.wooteco.wiki.document.fixture.DocumentFixture;
import com.wooteco.wiki.document.repository.DocumentRepository;
import com.wooteco.wiki.global.common.PageRequestDto;
import com.wooteco.wiki.global.exception.ErrorCode;
import com.wooteco.wiki.global.exception.WikiException;
import com.wooteco.wiki.log.domain.dto.LogResponse;
import com.wooteco.wiki.log.fixture.LogFixture;
import com.wooteco.wiki.log.repository.LogRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
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
    class findAllByCrewDocumentUuid {

        private PageRequestDto pageRequestDto = new PageRequestDto();
        private UUID documentUuid;
        private CrewDocument savedCrewDocument;

        @BeforeEach
        void setUp() {
            savedCrewDocument = documentRepository.save(
                    DocumentFixture.create("title", "content", "writer", 100L, LocalDateTime.now(), UUID.randomUUID()));
            documentUuid = savedCrewDocument.getUuid();

            logRepository.save(LogFixture.create("t1", "c1", "w1", 10L, LocalDateTime.now(), savedCrewDocument, 1L));
            logRepository.save(LogFixture.create("t1", "c2", "w2", 20L, LocalDateTime.now(), savedCrewDocument, 2L));
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
                    .containsExactly("t1", "t1");
            assertions.assertAll();
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
            assertThat(versions).containsExactly(1L, 2L);
        }

        @DisplayName("존재하지 않는 documentUuid로 요청 시 예외가 발생한다 : WikiException.DOCUMENT_NOT_FOUND")
        @Test
        void findAllByDocumentUuid_throwsException_byNonExistsDocumentUuid() {
            // given
            UUID invalidUuid = UUID.randomUUID();

            // when & then
            WikiException ex = assertThrows(WikiException.class,
                    () -> logService.findAllByDocumentUuid(invalidUuid, pageRequestDto));
            assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.DOCUMENT_NOT_FOUND);
        }

        @DisplayName("로그 저장 시 최신 version을 제공한다.")
        @Test
        void save_versionIsNumberedCorrectly() {
            // when
            CrewDocument updatedCrewDocument = savedCrewDocument.update("test_document_2", "contents", "writer1", 120L,
                    LocalDateTime.now());
            documentRepository.save(updatedCrewDocument);
            logService.save(updatedCrewDocument);

            // then
            Page<LogResponse> secondLogs = logService.findAllByDocumentUuid(savedCrewDocument.getUuid(), pageRequestDto);
            assertThat(secondLogs.getContent()).hasSize(3);
            assertThat(secondLogs.getContent().get(0).version()).isEqualTo(1L);
            assertThat(secondLogs.getContent().get(2).version()).isEqualTo(3L);
        }
    }
}
