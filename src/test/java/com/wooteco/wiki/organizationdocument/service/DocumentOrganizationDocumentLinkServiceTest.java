package com.wooteco.wiki.organizationdocument.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.wooteco.wiki.document.domain.Document;
import com.wooteco.wiki.document.fixture.DocumentFixture;
import com.wooteco.wiki.document.repository.DocumentRepository;
import com.wooteco.wiki.organizationdocument.domain.DocumentOrganizationDocumentLink;
import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import com.wooteco.wiki.organizationdocument.fixture.OrganizationDocumentFixture;
import com.wooteco.wiki.organizationdocument.repository.DocumentOrganizationDocumentLinkRepository;
import com.wooteco.wiki.organizationdocument.repository.OrganizationDocumentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DocumentOrganizationDocumentLinkServiceTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentOrganizationDocumentLinkService documentOrgDocLinkService;

    @Autowired
    private OrganizationDocumentRepository organizationDocumentRepository;

    @Autowired
    private DocumentOrganizationDocumentLinkRepository documentOrgDocLinkRepository;

    @DisplayName("문서 및 조직 문서 연결 기능")
    @Nested
    class Link {

        @DisplayName("특정 문서와 특정 조직 문서로 둘을 연결한다.")
        @Test
        void link_success_byDocumentAndOrganizationDocument() {
            // given
            Document document = DocumentFixture.createDefault();
            Document savedDocument = documentRepository.save(document);

            OrganizationDocument organizationDocument = OrganizationDocumentFixture.createDefault();
            OrganizationDocument savedOrganizationDocument = organizationDocumentRepository.save(organizationDocument);

            // when
            documentOrgDocLinkService.link(savedDocument, savedOrganizationDocument);
            DocumentOrganizationDocumentLink documentOrgDocLink = documentOrgDocLinkRepository.findByDocumentAndOrganizationDocument(
                    savedDocument, savedOrganizationDocument);

            // then
            assertSoftly(softly -> {
                softly.assertThat(documentOrgDocLink.getDocument().getId())
                        .isEqualTo(savedDocument.getId());
                softly.assertThat(documentOrgDocLink.getOrganizationDocument().getId())
                        .isEqualTo(savedOrganizationDocument.getId());
            });
        }
    }
}
