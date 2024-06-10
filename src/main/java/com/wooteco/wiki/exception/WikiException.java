package com.wooteco.wiki.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WikiException extends RuntimeException {
    private final ExceptionType exceptionType;

    public WikiException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public String getMessage() {
        return exceptionType.getErrorMessage();
    }

    public HttpStatus getStatus() {
        return exceptionType.getHttpStatus();
    }
}
