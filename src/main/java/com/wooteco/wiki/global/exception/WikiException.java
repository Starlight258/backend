package com.wooteco.wiki.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WikiException extends RuntimeException {
    
    private final ErrorCode errorCode;

    public WikiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }
}
