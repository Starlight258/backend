package com.wooteco.wiki.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.wooteco.wiki.document.service.UUIDService;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UUIDServiceTest {

    @InjectMocks
    private UUIDService uuidService;

    @Test
    @DisplayName("UUID를 생성한다")
    void generate() {
        // when
        String uuid = uuidService.generate().toString();

        // then
        assertThat(uuid).matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
    }

    @Test
    @DisplayName("생성된 UUID는 매번 다른 값이다")
    void generateDifferentUuid() {
        // when
        UUID uuid1 = uuidService.generate();
        UUID uuid2 = uuidService.generate();

        // then
        assertThat(uuid1).isNotEqualTo(uuid2);
    }
}
