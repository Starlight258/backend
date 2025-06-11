package com.wooteco.wiki.document.service;

import com.wooteco.wiki.document.domain.dto.DocumentResponse;
import com.wooteco.wiki.document.domain.dto.DocumentUuidResponse;
import com.wooteco.wiki.document.exception.DocumentNotFoundException;
import com.wooteco.wiki.document.fixture.DocumentFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DocumentServiceTest {

    @Autowired
    private DocumentService documentService;

    @Nested
    @DisplayName("문서 제목으로 조회하면 UUID를 반환하는 기능")
    class getUuidByTitle {

        @DisplayName("존재하는 문서 제목으로 조회할 경우 UUID를 반환한다")
        @Test
        void getUuidByTitle_success_byExistsDocumentTitle() {
            // given
            DocumentResponse documentResponse = documentService.post(
                    DocumentFixture.createDocumentCreateRequestDefault());

            // when
            DocumentUuidResponse documentUuidResponse = documentService.getUuidByTitle(documentResponse.getTitle());

            // then
            Assertions.assertThat(documentUuidResponse.uuid()).isEqualTo(documentResponse.getDocumentUUID());
        }

        @DisplayName("존재하지 않는 문서 제목으로 조회할 경우 예외를 반환한다 : DocumentNotFoundException")
        @Test
        void getUuidByTitle_success_byNonExistsDocumentTitle() {
            // when & then
            Assertions.assertThatThrownBy(
                    () -> documentService.getUuidByTitle("nonExistsDocumentTitle")
            ).isInstanceOf(DocumentNotFoundException.class);
        }
    }
}
