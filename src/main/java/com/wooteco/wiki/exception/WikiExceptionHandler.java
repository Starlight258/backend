package com.wooteco.wiki.exception;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.wooteco.wiki.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WikiExceptionHandler {
    @ExceptionHandler(WikiException.class)
    public ResponseEntity<ErrorResponse> handle(WikiException exception) {
        if (exception.getMessage().equals("제목이 겹치는 문서가 있습니다.")) {
            return ResponseEntity.status(CONFLICT)
                    .body(new ErrorResponse(exception.getMessage()));
        }
        if (exception.getMessage().equals("존재하지 않는 제목의 문서입니다.")) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ErrorResponse(exception.getMessage()));
        }
        if (exception.getMessage().equals("해당 로그가 존재하지 않습니다.")) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ErrorResponse(exception.getMessage()));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("알 수 없는 에러가 발생했습니다."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception exception) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse("알 수 없는 에러가 발생했습니다."));
    }
}
