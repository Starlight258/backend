package com.wooteco.wiki.global.exception;

public class PageBadRequestException extends BadRequestException {

    private static final String DEFAULT_MESSAGE = "페이지 요청 파라미터가 유효하지 않습니다.";

    public PageBadRequestException(String message) {
        super(message);
    }

    public PageBadRequestException() {
        this(DEFAULT_MESSAGE);
    }
}
