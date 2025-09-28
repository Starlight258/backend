package com.wooteco.wiki.document.service;

import com.wooteco.wiki.document.domain.dto.DocumentSearchResponse;
import com.wooteco.wiki.document.fixture.DocumentFixture;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CrewDocumentSearchServiceTest {

    @Autowired
    private DocumentSearchService documentSearchService;

    @Autowired
    private DocumentService documentService;

    @DisplayName("검색어로 시작하는 문서들을 찾아 리스트로 반환한다.")
    @Test
    void search_success() {
        // given
        documentService.postCrewDocument(
                DocumentFixture.createDocumentCreateRequest("title1", "content1", "writer1", 10L, UUID.randomUUID()));
        documentService.postCrewDocument(
                DocumentFixture.createDocumentCreateRequest("title2", "content2", "writer2", 11L, UUID.randomUUID()));

        // when
        List<DocumentSearchResponse> result = documentSearchService.search("title");

        // then
        Assertions.assertThat(result).hasSize(2);
    }

    @DisplayName("검색어와 일치하는 문서가 없으면 빈 리스트를 반환한다.")
    @Test
    void search_empty() {
        // given
        String keyword = "존재하지않는문서";

        // when
        List<DocumentSearchResponse> result = documentSearchService.search(keyword);

        // then
        Assertions.assertThat(result).isEmpty();
    }
} 
