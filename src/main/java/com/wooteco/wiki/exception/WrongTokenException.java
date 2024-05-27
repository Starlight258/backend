package com.wooteco.wiki.exception;

import org.springframework.http.HttpStatus;

public class WrongTokenException extends WikiException {
    public WrongTokenException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
