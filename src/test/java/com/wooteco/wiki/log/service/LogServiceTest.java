package com.wooteco.wiki.log.service;

import com.wooteco.wiki.document.domain.dto.DocumentCreateRequest;
import com.wooteco.wiki.document.domain.dto.DocumentResponse;
import com.wooteco.wiki.document.domain.dto.DocumentUpdateRequest;
import com.wooteco.wiki.document.fixture.DocumentFixture;
import com.wooteco.wiki.document.service.DocumentService;
import com.wooteco.wiki.log.domain.dto.LogResponse;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class LogServiceTest {

    @Autowired
    private LogService logService;
    @Autowired
    private DocumentService documentService;
    
    @Nested
    @DisplayName("uuid로 요청 시 로그 리스트 반환하는 기능")
    class getLogs {

        @DisplayName("로그가 존재할 때 uuid로 요청 시 List 형태로 로그를 반환한다")
        @Test
        void getLogs_success_bySomeData() {
            // given
            DocumentCreateRequest documentCreateRequest = DocumentFixture.createDocumentCreateRequest("title1", "content1", "writer1", 10L, UUID.randomUUID());
            DocumentResponse createDocumentResponse = documentService.post(documentCreateRequest);

            final String createDocumentUuidText = createDocumentResponse.getDocumentUUID().toString();
            DocumentUpdateRequest documentUpdateRequest = DocumentFixture.createDocumentUpdateRequest("title2", "content2", "writer2", 20L);
            DocumentResponse updateDocumentResponse = documentService.put(createDocumentUuidText, documentUpdateRequest);


            // when
            List<LogResponse> logs = logService.getLogs(updateDocumentResponse.getDocumentUUID());

            // then
            SoftAssertions softAssertions = new SoftAssertions();
            softAssertions.assertThat(logs).hasSize(2);
            softAssertions.assertThat(logs.get(0).writer()).isEqualTo(documentCreateRequest.getWriter());
            softAssertions.assertThat(logs.get(1).writer()).isEqualTo(documentUpdateRequest.getWriter());
            softAssertions.assertAll();
        }
    }
}
