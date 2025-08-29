package com.wooteco.wiki.organizationdocument.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.wooteco.wiki.document.domain.Document;
import com.wooteco.wiki.document.fixture.DocumentFixture;
import com.wooteco.wiki.document.repository.DocumentRepository;
import com.wooteco.wiki.organizationdocument.domain.DocumentOrganizationDocumentLink;
import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import com.wooteco.wiki.organizationdocument.fixture.OrganizationDocumentFixture;
import com.wooteco.wiki.organizationdocument.repository.DocumentOrganizationDocumentLinkRepository;
import com.wooteco.wiki.organizationdocument.repository.OrganizationDocumentRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DocumentOrganizationLinkServiceTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentOrganizationLinkService documentOrgDocLinkService;

    @Autowired
    private OrganizationDocumentRepository organizationDocumentRepository;

    @Autowired
    private DocumentOrganizationDocumentLinkRepository documentOrgDocLinkRepository;

    @DisplayName("문서 와 조직 문서 연결을 할 때에")
    @Nested
    class Link {

        private Document savedDocument;
        private OrganizationDocument savedOrganizationDocument;

        @BeforeEach
        void setUp() {
            Document document = DocumentFixture.createDefault();
            savedDocument = documentRepository.save(document);

            OrganizationDocument organizationDocument = OrganizationDocumentFixture.createDefault();
            savedOrganizationDocument = organizationDocumentRepository.save(organizationDocument);
        }

        @DisplayName("특정 문서와 특정 조직 문서로 둘을 연결한다.")
        @Test
        void link_success_byDocumentAndOrganizationDocument() {
            // when
            documentOrgDocLinkService.link(savedDocument, savedOrganizationDocument);

            // then
            DocumentOrganizationDocumentLink documentOrgDocLink = documentOrgDocLinkRepository.findByDocumentAndOrganizationDocument(
                    savedDocument, savedOrganizationDocument).get();

            assertSoftly(softly -> {
                softly.assertThat(documentOrgDocLink.getDocument().getId())
                        .isEqualTo(savedDocument.getId());
                softly.assertThat(documentOrgDocLink.getOrganizationDocument().getId())
                        .isEqualTo(savedOrganizationDocument.getId());
            });
        }
    }

    @DisplayName("문서와 조직 문서 연결 해제 할 때에")
    @Nested
    class UnLink {

        private Document savedDocument;
        private OrganizationDocument savedOrganizationDocument;

        @BeforeEach
        void setUp() {
            Document document = DocumentFixture.createDefault();
            savedDocument = documentRepository.save(document);

            OrganizationDocument organizationDocument = OrganizationDocumentFixture.createDefault();
            savedOrganizationDocument = organizationDocumentRepository.save(organizationDocument);

            documentOrgDocLinkService.link(savedDocument, savedOrganizationDocument);
        }

        @DisplayName("특정 문서와 특정 조직 문서의 연결을 해제한다")
        @Test
        void unLink_success_byDocumentAndOrganizationDocument() {
            // when
            documentOrgDocLinkService.unlink(savedDocument, savedOrganizationDocument);

            // then
            Optional<DocumentOrganizationDocumentLink> foundDocumentOrgDocLink = documentOrgDocLinkRepository.findByDocumentAndOrganizationDocument(
                    savedDocument, savedOrganizationDocument);

            assertThat(foundDocumentOrgDocLink).isEmpty();
        }

    }
}
