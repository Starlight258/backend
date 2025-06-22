package com.wooteco.wiki.global.common;

public record ResponseDto<T> (int page, int totalPage, T data){

    public static <T> ResponseDto<T> of(int page, int totalPage, T data) {
        return new ResponseDto<>(page, totalPage, data);
    }
}
