package com.wooteco.wiki.global.exception;


import org.springframework.http.HttpStatus;

public class BadRequestException extends WikiException {

    private static final String DEFAULT_MESSAGE = "잘못된 요청입니다.";

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException() {
        this(DEFAULT_MESSAGE);
    }
}
