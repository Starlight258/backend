package com.wooteco.wiki.document.exception;

import com.wooteco.wiki.global.exception.NotFoundException;

public class DocumentNotFoundException extends NotFoundException {

    private static final String DEFAULT_MESSAGE = "없는 문서입니다.";

    public DocumentNotFoundException(String message) {
        super(message);
    }

    public DocumentNotFoundException() {
        this(DEFAULT_MESSAGE);
    }
}
