package com.wooteco.wiki.document.exception;

import com.wooteco.wiki.global.exception.BadRequestException;

public class DocumentBadRequestException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "잘못된 요청입니다.";

    public DocumentBadRequestException(String message) {
        super(message);
    }

    public DocumentBadRequestException() {
        this(DEFAULT_MESSAGE);
    }
}
