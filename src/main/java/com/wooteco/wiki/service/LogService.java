package com.wooteco.wiki.service;

import com.wooteco.wiki.dto.LogDetailResponse;
import com.wooteco.wiki.entity.Log;
import com.wooteco.wiki.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    public LogDetailResponse getLogDetail(Long logId) {
        Log log = logRepository.getReferenceById(logId);

        return new LogDetailResponse(logId, log.getTitle(), log.getContents(), log.getWriter(), log.getGenerateTime());
    }
}
