package com.wooteco.wiki.global.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends WikiException {

    private static final String DEFAULT_MESSAGE = "찾을 수 없습니다";

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public NotFoundException() {
        this(DEFAULT_MESSAGE);
    }
}
