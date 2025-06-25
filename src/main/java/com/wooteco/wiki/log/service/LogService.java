package com.wooteco.wiki.log.service;

import com.wooteco.wiki.document.exception.DocumentBadRequestException;
import com.wooteco.wiki.document.exception.DocumentNotFoundException;
import com.wooteco.wiki.document.repository.DocumentRepository;
import com.wooteco.wiki.global.common.PageRequestDto;
import com.wooteco.wiki.log.domain.Log;
import com.wooteco.wiki.log.domain.dto.LogDetailResponse;
import com.wooteco.wiki.log.domain.dto.LogResponse;
import com.wooteco.wiki.log.repository.LogRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;
    private final DocumentRepository documentRepository;

    public LogDetailResponse getLogDetail(Long logId) {
        Log log = logRepository.findById(logId)
                .orElseThrow(() -> new DocumentNotFoundException("해당 로그가 존재하지 않습니다."));
        return new LogDetailResponse(logId, log.getTitle(), log.getContents(), log.getWriter(),
                log.getGenerateTime());
    }

    public Page<LogResponse> findAllByDocumentUuid(UUID documentUuid, PageRequestDto pageRequestDto) {
        Long documentId = documentRepository.findIdByUuid(documentUuid)
                .orElseThrow(DocumentBadRequestException::new);

        Pageable pageable = pageRequestDto.toPageable();
        Page<Log> logs = logRepository.findAllByDocumentId(documentId, pageable);
        List<Log> content = logs.getContent();
        long offset = logs.getPageable().getOffset();

        List<LogResponse> responses = IntStream.range(0, content.size())
                .mapToObj(i -> LogResponse.of(content.get(i), offset + i + 1))
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, logs.getTotalElements());
    }
}
