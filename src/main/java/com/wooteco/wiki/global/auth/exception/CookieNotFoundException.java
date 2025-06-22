package com.wooteco.wiki.global.auth.exception;

import com.wooteco.wiki.global.exception.NotFoundException;

public class CookieNotFoundException extends NotFoundException {

    private static final String DEFAULT_MESSAGE = "토큰을 찾을 수 없습니다.";

    public CookieNotFoundException(String message) {
        super(message);
    }

    public CookieNotFoundException() {
        this(DEFAULT_MESSAGE);
    }
}
