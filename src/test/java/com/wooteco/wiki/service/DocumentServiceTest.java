package com.wooteco.wiki.service;

import static com.wooteco.wiki.exception.ExceptionType.DOCUMENT_DUPLICATE;
import static com.wooteco.wiki.exception.ExceptionType.DOCUMENT_NOT_FOUND;
import static com.wooteco.wiki.exception.ExceptionType.MEMBER_NOT_FOUNT;

import com.wooteco.wiki.domain.Member;
import com.wooteco.wiki.dto.DocumentCreateRequest;
import com.wooteco.wiki.dto.DocumentUpdateRequest;
import com.wooteco.wiki.exception.WikiException;
import com.wooteco.wiki.repository.MemberRepository;
import com.wooteco.wiki.testinfra.ActiveProfileSpringBootTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DocumentServiceTest extends ActiveProfileSpringBootTest {
    @Autowired
    private DocumentService documentService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("없는 회원이 문서를 작성하려 할 때 예외가 발생하는지 확인")
    void postFailWhenInvalidMemberId() {
        Assertions.assertThatThrownBy(() -> documentService.post(1, new DocumentCreateRequest("title", "cotents", 1L)))
                .isInstanceOf(WikiException.class)
                .hasMessage(MEMBER_NOT_FOUNT.getErrorMessage());
    }

    @Test
    @DisplayName("이름이 겹치는 문서를 작성하려 할 때 예외가 발생하는지 확인")
    void postFailWhenDuplicateTitle() {
        Member member = Member.builder()
                .email("email@email.com")
                .password("qwer123!")
                .nickname("로빈")
                .build();
        memberRepository.save(member);
        documentService.post(member.getMemberId(), new DocumentCreateRequest("title", "cotents", 1L));

        Assertions.assertThatThrownBy(
                        () -> documentService.post(member.getMemberId(), new DocumentCreateRequest("title", "cotents", 1L)))
                .isInstanceOf(WikiException.class)
                .hasMessage(DOCUMENT_DUPLICATE.getErrorMessage());
    }

    @Test
    @DisplayName("문서가 하나도 없을 때 실패하는지 확인")
    void getRandomFailWhenNoDocument() {
        Assertions.assertThatThrownBy(() -> documentService.getRandom())
                .isInstanceOf(WikiException.class)
                .hasMessage(DOCUMENT_NOT_FOUND.getErrorMessage());
    }

    @Test
    @DisplayName("그 이름의 문서가 없는 경우 예외가 발생하는지 확인")
    void getFailWhenDocumentNotFound() {
        Assertions.assertThatThrownBy(() -> documentService.get("title"))
                .isInstanceOf(WikiException.class)
                .hasMessage(DOCUMENT_NOT_FOUND.getErrorMessage());
    }

    @Test
    @DisplayName("없는 회원이 문서를 수정하려 할 때 예외가 발생하는지 확인")
    void putFailWhenMemberNotFound() {
        Member member = Member.builder()
                .email("email@email.com")
                .password("qwer123!")
                .nickname("로빈")
                .build();
        memberRepository.save(member);
        documentService.post(member.getMemberId(), new DocumentCreateRequest("title", "cotents", 1L));

        long invalidMemberId = member.getMemberId() + 100;
        DocumentUpdateRequest updateRequest = new DocumentUpdateRequest("content", 1L);
        Assertions.assertThatThrownBy(() -> documentService.put(invalidMemberId, "title", updateRequest))
                .isInstanceOf(WikiException.class)
                .hasMessage(MEMBER_NOT_FOUNT.getErrorMessage());
    }

    @Test
    @DisplayName("없는 문서를 수정하려 할 때 예외가 발생하는지 확인")
    void putFailWhenDocumentNotFound() {
        Member member = Member.builder()
                .email("email@email.com")
                .password("qwer123!")
                .nickname("로빈")
                .build();
        memberRepository.save(member);

        DocumentUpdateRequest updateRequest = new DocumentUpdateRequest("content", 1L);
        Assertions.assertThatThrownBy(() -> documentService.put(member.getMemberId(), "title", updateRequest))
                .isInstanceOf(WikiException.class)
                .hasMessage(DOCUMENT_NOT_FOUND.getErrorMessage());
    }
}
