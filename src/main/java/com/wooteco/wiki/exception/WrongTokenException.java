package com.wooteco.wiki.exception;

import org.springframework.http.HttpStatus;

public class WrongTokenException extends WikiException {

    private static final String DEFAULT_MESSAGE_FILED = "잘못된 토큰입니다.";

    public WrongTokenException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

    public WrongTokenException() {
        this(DEFAULT_MESSAGE_FILED);
    }
}
