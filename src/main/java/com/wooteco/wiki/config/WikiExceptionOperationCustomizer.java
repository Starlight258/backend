package com.wooteco.wiki.config;

import com.wooteco.wiki.annotation.ErrorApiResponse;
import com.wooteco.wiki.dto.ErrorResponse;
import com.wooteco.wiki.exception.ExceptionType;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class WikiExceptionOperationCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        ErrorApiResponse errorApiResponseAnnotation = handlerMethod.getMethodAnnotation(ErrorApiResponse.class);
        if (errorApiResponseAnnotation != null) {
            ExceptionType[] exceptionTypes = errorApiResponseAnnotation.value();
            ApiResponses responses = operation.getResponses();
            Map<Integer, List<ExampleHolder>> collect = Arrays.stream(exceptionTypes).map(exceptionType -> {
                Example example = new Example();
                example.setValue(new ErrorResponse(exceptionType.getErrorMessage()));
                return new ExampleHolder(exceptionType.getErrorMessage(), example,
                        exceptionType.getHttpStatus().value());
            }).collect(Collectors.groupingBy(exampleHolder -> exampleHolder.statusCode));
            collect.forEach((statusCode, exampleHolders) -> {
                Content content = new Content();
                MediaType mediaType = new MediaType();
                ApiResponse apiResponse = new ApiResponse();
                for (ExampleHolder exampleHolder : exampleHolders) {
                    mediaType.addExamples(exampleHolder.key, exampleHolder.example);
                }
                content.addMediaType("application/json", mediaType);
                apiResponse.setContent(content);
                responses.addApiResponse(String.valueOf(statusCode), apiResponse);
            });
        }
        return operation;
    }

    private record ExampleHolder(String key, Example example, int statusCode) {
    }
}
