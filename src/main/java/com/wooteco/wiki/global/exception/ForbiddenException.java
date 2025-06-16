package com.wooteco.wiki.global.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends WikiException {

    private static final String DEFAULT_MESSAGE = "권한이 알맞지 않습니다.";

    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }

    public ForbiddenException() {
        this(DEFAULT_MESSAGE);
    }
}
