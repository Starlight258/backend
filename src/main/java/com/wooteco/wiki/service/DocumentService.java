package com.wooteco.wiki.service;

import com.wooteco.wiki.domain.Document;
import com.wooteco.wiki.domain.Log;
import com.wooteco.wiki.domain.Member;
import com.wooteco.wiki.dto.DocumentCreateRequest;
import com.wooteco.wiki.dto.DocumentFindAllByRecentResponse;
import com.wooteco.wiki.dto.DocumentResponse;
import com.wooteco.wiki.dto.DocumentUpdateRequest;
import com.wooteco.wiki.exception.DocumentNotFoundException;
import com.wooteco.wiki.exception.DuplicateDocumentException;
import com.wooteco.wiki.exception.MemberNotFoundException;
import com.wooteco.wiki.repository.DocumentRepository;
import com.wooteco.wiki.repository.LogRepository;
import com.wooteco.wiki.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final LogRepository logRepository;
    private final MemberRepository memberRepository;
    private final Random random = new Random();

    public DocumentResponse post(long memberId, DocumentCreateRequest documentCreateRequest) {
        Member writer = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("없는 회원입니다."));
        String title = documentCreateRequest.title();
        String contents = documentCreateRequest.contents();
        Long documentBytes = documentCreateRequest.documentBytes();

        if (documentRepository.existsByTitle(title)) {
            throw new DuplicateDocumentException("제목이 겹치는 문서가 있습니다.");
        }

        Document document = Document.builder()
                .title(title)
                .contents(contents)
                .writer(writer)
                .documentBytes(documentBytes)
                .generateTime(LocalDateTime.now())
                .build();
        Document save = documentRepository.save(document);

        Log log = Log.builder()
                .title(title)
                .contents(contents)
                .writer(writer)
                .documentBytes(documentBytes)
                .generateTime(save.getGenerateTime())
                .build();
        logRepository.save(log);

        return mapToResponse(save);
    }

    private DocumentResponse mapToResponse(Document document) {
        long documentId = document.getDocumentId();
        String title = document.getTitle();
        String contents = document.getContents();
        String writer = document.getWriter().getNickname();
        LocalDateTime generateTime = document.getGenerateTime();
        return new DocumentResponse(documentId, title, contents, writer, generateTime);
    }

    public DocumentResponse getRandom() {
        List<Document> allDocuments = documentRepository.findAll();
        int allDocumentsCount = allDocuments.size();
        if (allDocumentsCount == 0) {
            throw new DocumentNotFoundException("문서가 없습니다.");
        }
        int randomIndex = random.nextInt(allDocumentsCount);
        Document document = allDocuments.get(randomIndex);
        return mapToResponse(document);
    }

    public DocumentResponse get(String title) {
        Optional<Document> byTitle = documentRepository.findByTitle(title);
        return byTitle.map(this::mapToResponse)
                .orElseThrow(() -> new DocumentNotFoundException("없는 문서입니다."));
    }

    public DocumentResponse put(long memberId, String title, DocumentUpdateRequest documentUpdateRequest) {
        String contents = documentUpdateRequest.contents();
        Member writer = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("없는 회원입니다."));
        Long documentBytes = documentUpdateRequest.documentBytes();

        Document document = documentRepository.findByTitle(title)
                .orElseThrow(() -> new DocumentNotFoundException("존재하지 않는 제목의 문서입니다."));
        document.update(contents, writer, documentBytes, LocalDateTime.now());

        Log log = Log.builder()
                .title(title)
                .contents(contents)
                .writer(writer)
                .documentBytes(documentBytes)
                .generateTime(document.getGenerateTime())
                .build();
        logRepository.save(log);

        return mapToResponse(document);
    }

    public DocumentFindAllByRecentResponse getRecentDocuments() {
        List<Document> documents = documentRepository.findAllByOrderByGenerateTimeDesc();
        return DocumentFindAllByRecentResponse.of(documents);
    }
}
