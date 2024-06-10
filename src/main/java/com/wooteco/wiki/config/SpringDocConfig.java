package com.wooteco.wiki.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    private final WikiExceptionOperationCustomizer exceptionOperationCustomizer;
    private final SuccessOperationCustomizer successOperationCustomizer;

    public SpringDocConfig(WikiExceptionOperationCustomizer exceptionOperationCustomizer,
                           SuccessOperationCustomizer successOperationCustomizer) {
        this.exceptionOperationCustomizer = exceptionOperationCustomizer;
        this.successOperationCustomizer = successOperationCustomizer;
    }

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .addOperationCustomizer(successOperationCustomizer)
                .addOperationCustomizer(exceptionOperationCustomizer)
                .group("위키")
                .pathsToMatch("/**")
                .build();
    }
}
