package com.wooteco.wiki;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = {"local", "info-logging"})
class WikiApplicationTests {

    @Test
    void contextLoads() {
    }

}
