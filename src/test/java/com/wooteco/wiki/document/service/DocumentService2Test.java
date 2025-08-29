package com.wooteco.wiki.document.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.wooteco.wiki.document.domain.Document;
import com.wooteco.wiki.document.dto.DocumentOrganizationDocumentCreateRequest;
import com.wooteco.wiki.document.fixture.DocumentFixture;
import com.wooteco.wiki.document.repository.DocumentRepository;
import com.wooteco.wiki.organizationdocument.domain.OrganizationDocument;
import com.wooteco.wiki.organizationdocument.dto.OrganizationDocumentTitleAndUuidResponse;
import com.wooteco.wiki.organizationdocument.dto.response.OrganizationDocumentResponse;
import com.wooteco.wiki.organizationdocument.fixture.OrganizationDocumentFixture;
import com.wooteco.wiki.organizationdocument.repository.OrganizationDocumentRepository;
import com.wooteco.wiki.organizationdocument.service.DocumentOrganizationDocumentLinkService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DocumentService2Test {

    @Autowired
    private DocumentService2 documentService;

    @Autowired
    private DocumentOrganizationDocumentLinkService documentOrganizationDocumentLinkService;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private OrganizationDocumentRepository organizationDocumentRepository;

    private Document savedDocument;
    private UUID savedDocumentUuid;

    @BeforeEach
    void setUp() {
        Document document = DocumentFixture.createDefault();
        savedDocument = documentRepository.save(document);
        savedDocumentUuid = savedDocument.getUuid();
    }

    @DisplayName("특정 문서에 대한 조직 문서 제목과 Uuid들을 조회할 때")
    @Nested
    class readOrganizationTitleAndUuid {

        @BeforeEach
        void setUp() {
            List<OrganizationDocument> organizationDocuments = List.of(
                    OrganizationDocumentFixture.create("title1", "defaultContent", "defaultWriter", 10L, UUID.randomUUID(), LocalDateTime.now()),
                    OrganizationDocumentFixture.create("title2", "defaultContent", "defaultWriter", 10L, UUID.randomUUID(), LocalDateTime.now())
            );
            organizationDocumentRepository.saveAll(organizationDocuments);

            for (OrganizationDocument organizationDocument : organizationDocuments) {
                documentOrganizationDocumentLinkService.link(savedDocument, organizationDocument);
            }
        }

        @DisplayName("존재하는 특정 문서의 Uuid로 요청한다면 조직 문서들이 올바르게 조회된다")
        @Test
        void readOrganizationTitleAndUuid_success_byExistingDocumentUuid() {
            // when
            List<OrganizationDocumentTitleAndUuidResponse> organizationDocumentTitleAndUuidResponses = documentService.readOrganizationTitleAndUuid(
                    savedDocumentUuid);

            // then
            assertSoftly(softy -> {
                        softy.assertThat(organizationDocumentTitleAndUuidResponses.size()).isEqualTo(2);
                        softy.assertThat(organizationDocumentTitleAndUuidResponses.stream()
                                .map(OrganizationDocumentTitleAndUuidResponse::title)
                        ).containsExactlyInAnyOrder("title1", "title2");
                }
            );
        }
    }

    @DisplayName("특정 문서에 대해 조직 문서를 추가할 때에")
    @Nested
    class addOrganizationDocument {

        @DisplayName("존재하는 특정 문서의 uuid로 요청한다면, 조직 문서가 추가된다")
        @Test
        void addOrganizationDocument_success_byExistingDocumentUuid() {
            // given
            DocumentOrganizationDocumentCreateRequest documentOrganizationDocumentCreateRequestDefault = OrganizationDocumentFixture.createDocumentOrganizationDocumentCreateRequest("title1", "defaultContents", "defaultWriter", 10L, UUID.randomUUID());

            // when
            documentService.addOrganizationDocument(savedDocumentUuid, documentOrganizationDocumentCreateRequestDefault);

            // then
            List<OrganizationDocumentResponse> organizationDocumentResponsesByDocument = documentOrganizationDocumentLinkService.findOrganizationDocumentResponsesByDocument(
                    savedDocument);
            assertSoftly(softy -> {
                        softy.assertThat(organizationDocumentResponsesByDocument.size()).isEqualTo(1);
                        softy.assertThat(organizationDocumentResponsesByDocument.stream()
                                .map(OrganizationDocumentResponse::title)
                        ).containsExactlyInAnyOrder("title1");
                    }
            );
        }
    }
}
