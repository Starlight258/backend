package com.wooteco.wiki.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class DocumentNotFoundException extends WikiException {
    public DocumentNotFoundException(String message) {
        super(message, NOT_FOUND);
    }
}
