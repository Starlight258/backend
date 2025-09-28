package com.wooteco.wiki.log.fixture;

import com.wooteco.wiki.document.domain.CrewDocument;
import com.wooteco.wiki.log.domain.Log;
import java.time.LocalDateTime;

public class LogFixture {

    public static Log create(String title, String contents, String writer, long documentBytes,
                             LocalDateTime generateTime, CrewDocument crewDocument, Long version) {
        return new Log(title, contents, writer, documentBytes, generateTime, crewDocument, version);
    }
}
