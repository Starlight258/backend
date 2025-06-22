package com.wooteco.wiki.document.service;

import com.wooteco.wiki.document.domain.Document;
import com.wooteco.wiki.document.domain.dto.DocumentCreateRequest;
import com.wooteco.wiki.document.domain.dto.DocumentResponse;
import com.wooteco.wiki.document.domain.dto.DocumentUuidResponse;
import com.wooteco.wiki.document.exception.DocumentNotFoundException;
import com.wooteco.wiki.document.fixture.DocumentFixture;
import com.wooteco.wiki.document.repository.DocumentRepository;
import com.wooteco.wiki.global.common.PageRequestDto;
import com.wooteco.wiki.global.exception.PageBadRequestException;
import com.wooteco.wiki.log.repository.LogRepository;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DocumentServiceTest {

    @Autowired
    private DocumentService documentService;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private LogRepository logRepository;

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

    @Nested
    @DisplayName("문서 전체 조회 기능")
    class findAll {

        @Nested
        @DisplayName("문서 전체 조회 기능 : 데이터의 수")
        class findAll_data {

            PageRequestDto pageRequestDto = new PageRequestDto();

            @DisplayName("저장된 문서가 존재할 때 요청 시 List 형태로 반환한다")
            @Test
            void findAll_success_bySomeData() {
                // given
                List<DocumentCreateRequest> documentCreateRequests = List.of(
                        DocumentFixture.createDocumentCreateRequest("title1", "content1", "writer1", 10L,
                                UUID.randomUUID()),
                        DocumentFixture.createDocumentCreateRequest("title2", "content2", "writer2", 11L,
                                UUID.randomUUID())
                );

                // when
                for (DocumentCreateRequest documentRequestDto : documentCreateRequests) {
                    documentService.post(documentRequestDto);
                }

                // then
                Assertions.assertThat(documentService.findAll(pageRequestDto)).hasSize(documentCreateRequests.size());
            }

            @DisplayName("저장된 문서가 존재하지 않을 때 요청 시 예외 없이 빈 리스트를 반환한다")
            @Test
            void findAll_success_byNoData() {
                // when & then
                Assertions.assertThat(documentService.findAll(pageRequestDto)).hasSize(0);
            }
        }

        @Nested
        @DisplayName("문서 전체 조회 기능 : Pageable")
        class findAll_pageable {

            List<DocumentCreateRequest> documentCreateRequests;

            @BeforeEach
            public void beforeEach() {
                documentCreateRequests = List.of(
                        DocumentFixture.createDocumentCreateRequest("title1", "content1", "writer1", 10L, UUID.randomUUID()),
                        DocumentFixture.createDocumentCreateRequest("title2", "content2", "writer2", 11L, UUID.randomUUID()),
                        DocumentFixture.createDocumentCreateRequest("title3", "content3", "writer3", 13L, UUID.randomUUID()),
                        DocumentFixture.createDocumentCreateRequest("title4", "content4", "writer4", 14L, UUID.randomUUID()),
                        DocumentFixture.createDocumentCreateRequest("title5", "content5", "writer5", 15L, UUID.randomUUID()),
                        DocumentFixture.createDocumentCreateRequest("title6", "content6", "writer6", 16L, UUID.randomUUID()),
                        DocumentFixture.createDocumentCreateRequest("title7", "content7", "writer7", 17L, UUID.randomUUID()),
                        DocumentFixture.createDocumentCreateRequest("title8", "content8", "writer8", 18L, UUID.randomUUID()),
                        DocumentFixture.createDocumentCreateRequest("title9", "content9", "writer9", 19L, UUID.randomUUID()),
                        DocumentFixture.createDocumentCreateRequest("title10", "content10", "writer10", 110L, UUID.randomUUID()),
                        DocumentFixture.createDocumentCreateRequest("title11", "content11", "writer11", 11L, UUID.randomUUID()),
                        DocumentFixture.createDocumentCreateRequest("title12", "content12", "writer12", 11L, UUID.randomUUID()),
                        DocumentFixture.createDocumentCreateRequest("title13", "content13", "writer13", 11L, UUID.randomUUID()),
                        DocumentFixture.createDocumentCreateRequest("title14", "content14", "writer14", 11L, UUID.randomUUID())
                );
            }

            @DisplayName("PageRequestDto의 default 값으로 동작하는 지 확인")
            @Test
            void findAll_success_byPageRequestDtoDefault() {
                // given
                PageRequestDto pageRequestDto = new PageRequestDto();

                for (DocumentCreateRequest documentRequestDto : documentCreateRequests) {
                    documentService.post(documentRequestDto);
                }

                // when
                Page<@NotNull Document> documentPages = documentService.findAll(pageRequestDto);

                // then
                SoftAssertions softAssertions = new SoftAssertions();
                softAssertions.assertThat(documentPages.getTotalElements()).isEqualTo(documentCreateRequests.size());
                softAssertions.assertThat(documentPages.getNumber()).isEqualTo(0);
                softAssertions.assertThat(documentPages.getTotalPages()).isEqualTo(2);
                softAssertions.assertAll();
            }

            @DisplayName("PageRequestDto의 필드 값을 수정한 값으로 동작하는 지 확인")
            @Test
            void findAll_success_byPageRequestDto() {
                // given
                PageRequestDto pageRequestDto = new PageRequestDto();
                pageRequestDto.setPageNumber(1);
                pageRequestDto.setPageSize(5);
                pageRequestDto.setSort("uuid");
                pageRequestDto.setSortDirection("DESC");

                for (DocumentCreateRequest documentRequestDto : documentCreateRequests) {
                    documentService.post(documentRequestDto);
                }

                // when
                Page<@NotNull Document> documentPages = documentService.findAll(pageRequestDto);

                // then
                SoftAssertions softAssertions = new SoftAssertions();
                softAssertions.assertThat(documentPages.getTotalElements()).isEqualTo(documentCreateRequests.size());
                softAssertions.assertThat(documentPages.getNumber()).isEqualTo(1);
                softAssertions.assertThat(documentPages.getTotalPages()).isEqualTo(3);
                softAssertions.assertAll();
            }
            
            @DisplayName("PageRequestDto 필드 중 pageNumber 와 pageSize는 음수가 불가능하도록 확인")
            @Test
            void findAll_throwsException_byNegativeNumber() {
                // given
                PageRequestDto pageRequestDto = new PageRequestDto();
                pageRequestDto.setPageNumber(-1);
                pageRequestDto.setPageSize(5);

                for (DocumentCreateRequest documentRequestDto : documentCreateRequests) {
                    documentService.post(documentRequestDto);
                }

                // when & then
                Assertions.assertThatThrownBy(
                        () -> documentService.findAll(pageRequestDto)
                ).isInstanceOf(PageBadRequestException.class);
            }
        }
    }
    
    @Nested
    @DisplayName("문서 id로 삭제 기능")
    class deleteById {
        
        @DisplayName("존재하는 문서 id일 경우 문서가 로그들과 함께 삭제된다")
        @Test
        void deleteById_success_byExistsId() {
            // given
            DocumentResponse documentResponse = documentService.post(DocumentFixture.createDocumentCreateRequest("title1", "content1", "writer1", 10L, UUID.randomUUID()));

            // before then
            Assertions.assertThat(documentRepository.findAll()).hasSize(1);
            Assertions.assertThat(logRepository.findAll()).hasSize(1);

            // when
            documentService.deleteById(documentResponse.getDocumentId());

            // after then
            Assertions.assertThat(documentRepository.findAll()).hasSize(0);
            Assertions.assertThat(logRepository.findAll()).hasSize(0);
        }

        @DisplayName("존재하지 않는 문서의 id일 경우 예외가 발생한다 : DocumentNotFoundException")
        @Test
        void deleteById_throwsException_byNonExistsId() {
            // when & then
            Assertions.assertThatThrownBy(
                    () -> documentService.deleteById(Long.MAX_VALUE)
            ).isInstanceOf(DocumentNotFoundException.class);
        }
    }
}
