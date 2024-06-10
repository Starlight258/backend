package com.wooteco.wiki.service;

import static com.wooteco.wiki.exception.ExceptionType.DOCUMENT_DUPLICATE;
import static com.wooteco.wiki.exception.ExceptionType.DOCUMENT_NOT_FOUND;
import static com.wooteco.wiki.exception.ExceptionType.MEMBER_NOT_FOUNT;

import com.wooteco.wiki.domain.Document;
import com.wooteco.wiki.domain.Log;
import com.wooteco.wiki.domain.Member;
import com.wooteco.wiki.dto.DocumentCreateRequest;
import com.wooteco.wiki.dto.DocumentFindAllByRecentResponse;
import com.wooteco.wiki.dto.DocumentResponse;
import com.wooteco.wiki.dto.DocumentUpdateRequest;
import com.wooteco.wiki.exception.WikiException;
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
                .orElseThrow(() -> new WikiException(MEMBER_NOT_FOUNT));
        String title = documentCreateRequest.title();
        String contents = documentCreateRequest.contents();
        Long documentBytes = documentCreateRequest.documentBytes();

        if (documentRepository.existsByTitle(title)) {
            throw new WikiException(DOCUMENT_DUPLICATE);
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
            throw new WikiException(DOCUMENT_NOT_FOUND);
        }
        int randomIndex = random.nextInt(allDocumentsCount);
        Document document = allDocuments.get(randomIndex);
        return mapToResponse(document);
    }

    public DocumentResponse get(String title) {
        Optional<Document> byTitle = documentRepository.findByTitle(title);
        return byTitle.map(this::mapToResponse)
                .orElseThrow(() -> new WikiException(DOCUMENT_NOT_FOUND));
    }

    public DocumentResponse put(long memberId, String title, DocumentUpdateRequest documentUpdateRequest) {
        String contents = documentUpdateRequest.contents();
        Member writer = memberRepository.findById(memberId)
                .orElseThrow(() -> new WikiException(MEMBER_NOT_FOUNT));
        Long documentBytes = documentUpdateRequest.documentBytes();

        Document document = documentRepository.findByTitle(title)
                .orElseThrow(() -> new WikiException(DOCUMENT_NOT_FOUND));
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
