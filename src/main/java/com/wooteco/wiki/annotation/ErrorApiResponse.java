package com.wooteco.wiki.annotation;

import com.wooteco.wiki.exception.ExceptionType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorApiResponse {
    ExceptionType[] value();
}
