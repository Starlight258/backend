package com.wooteco.wiki.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionType {
    MEMBER_NOT_FOUNT(HttpStatus.NOT_FOUND, "없는 회원입니다."),
    DOCUMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "없는 문서입니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "토큰이 없거나 유효기간이 지났습니다."),
    DOCUMENT_DUPLICATE(HttpStatus.CONFLICT, "같은 제목의 문서가 이미 있습니다."),
    EMAIL_DUPLICATE(HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    EMAIL_INVALID(HttpStatus.BAD_REQUEST, "잘못된 이메일입니다."),
    PASSWORD_INVALID(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다."),
    LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "이메일이나 비밀번호가 잘못되었습니다.");
    private final HttpStatus httpStatus;
    private final String errorMessage;

    ExceptionType(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
