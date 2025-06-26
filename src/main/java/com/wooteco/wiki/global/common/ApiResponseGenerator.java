package com.wooteco.wiki.global.common;

import org.springframework.http.HttpStatus;

public class ApiResponseGenerator {

    private static final String SUCCESS_MESSAGE = "SUCCESS";

    public static <D> ApiResponse<ApiResponse.SuccessBody<D>> success(D data) {
        return new ApiResponse<>(new ApiResponse.SuccessBody<>(data, SUCCESS_MESSAGE), HttpStatus.OK);
    }
    
    public static ApiResponse<ApiResponse.SuccessBody<Void>> success(HttpStatus status) {
        return new ApiResponse<>(new ApiResponse.SuccessBody<>(null, SUCCESS_MESSAGE), status);
    }

    public static ApiResponse<ApiResponse.FailureBody> failure(String message, HttpStatus status) {
        return new ApiResponse<>(new ApiResponse.FailureBody(message), status);
    }
}
