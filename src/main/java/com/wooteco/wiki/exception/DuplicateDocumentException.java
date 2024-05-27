package com.wooteco.wiki.exception;

import static org.springframework.http.HttpStatus.CONFLICT;

public class DuplicateDocumentException extends WikiException {
    public DuplicateDocumentException(String message) {
        super(message, CONFLICT);
    }
}
