package com.wooteco.wiki.organizationdocument.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import com.wooteco.wiki.organizationdocument.dto.request.OrganizationDocumentUpdateRequest;
import com.wooteco.wiki.organizationdocument.fixture.OrganizationDocumentFixture;
import com.wooteco.wiki.organizationdocument.repository.OrganizationDocumentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrganizationDocumentServiceTest {

    @Autowired
    private OrganizationDocumentRepository organizationDocumentRepository;

    @Autowired
    private OrganizationDocumentService organizationDocumentService;

    @DisplayName("조직 문서를 수정할 때")
    @Nested
    class Update {

        @DisplayName("전달된 값으로 갱신된다.")
        @Test
        void updateOrganizationDocument_success_byValidData() {
            // given
            String updateTitle = "updateTitle";
            String updateContents = "updateContents";
            String updateWriter = "updateWriter";
            Long updateDocumentBytes = 200L;

            OrganizationDocument organizationDocument = OrganizationDocumentFixture.createDefault();
            organizationDocumentRepository.save(organizationDocument);
            OrganizationDocumentUpdateRequest organizationDocumentUpdateRequest = new OrganizationDocumentUpdateRequest(
                    updateTitle, updateContents, updateWriter, updateDocumentBytes, organizationDocument.getUuid());

            // when
            organizationDocumentService.update(organizationDocumentUpdateRequest);

            OrganizationDocument foundOrganizationDocument = organizationDocumentRepository.findByUuid(
                    organizationDocument.getUuid()).orElseThrow();

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundOrganizationDocument.getTitle()).isEqualTo(updateTitle);
                softly.assertThat(foundOrganizationDocument.getContents()).isEqualTo(updateContents);
                softly.assertThat(foundOrganizationDocument.getWriter()).isEqualTo(updateWriter);
                softly.assertThat(foundOrganizationDocument.getDocumentBytes()).isEqualTo(updateDocumentBytes);
            });
        }
    }
}
