package com.wooteco.wiki.service;

import com.wooteco.wiki.dto.LogResponse;
import com.wooteco.wiki.exception.DocumentNotFoundException;
import com.wooteco.wiki.testinfra.ActiveProfileSpringBootTest;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class LogServiceTest extends ActiveProfileSpringBootTest {
    @Autowired
    private LogService logService;

    @Test
    @DisplayName("없는 아이디로 로그를 조회했을 때 예외가 발생하는지 확인")
    void getLogDetail() {
        Assertions.assertThatThrownBy(() -> logService.getLogDetail(1L))
                .isInstanceOf(DocumentNotFoundException.class);
    }

    @Test
    @DisplayName("없는 문서의 로그를 조회했을 때 빈 결과를 반환하는지 확인")
    void getLogs() {
        List<LogResponse> logResponses = logService.getLogs("title");
        Assertions.assertThat(logResponses)
                .isEmpty();
    }
}
