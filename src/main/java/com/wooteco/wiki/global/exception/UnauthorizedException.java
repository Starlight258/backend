package com.wooteco.wiki.global.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends WikiException {

    private static final String DEFAULT_MESSAGE = "인증이 필요합니다.";

    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException() {
        this(DEFAULT_MESSAGE);
    }
}
