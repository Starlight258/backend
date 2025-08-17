package com.wooteco.wiki.global.exception;

import com.wooteco.wiki.global.common.ApiResponse;
import com.wooteco.wiki.global.common.ApiResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class WikiExceptionHandler {

    @ExceptionHandler(WikiException.class)
    public ApiResponse<ApiResponse.FailureBody> handle(WikiException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponseGenerator.failure(exception.getErrorCode(), exception.getMessage(),
                exception.getHttpStatus());
    }

//    @ExceptionHandler(Exception.class)
//    public ApiResponse<ApiResponse.FailureBody> handle(Exception exception) {
//        log.error(exception.getMessage(), exception);
//        return ApiResponseGenerator.failure(ErrorCode.UNKNOWN_ERROR, "An unknown error occurred.",
//                HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
