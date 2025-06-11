package com.wooteco.wiki.document.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.wooteco.wiki.global.exception.WikiException;

public class DocumentNotFoundException extends WikiException {

    private static final String DEFAULT_MESSAGE = "없는 문서입니다.";

    public DocumentNotFoundException(String message) {
        super(message, NOT_FOUND);
    }

    public DocumentNotFoundException() {
        this(DEFAULT_MESSAGE);
    }
}
