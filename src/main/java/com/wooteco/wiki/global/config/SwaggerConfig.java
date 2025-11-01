package com.wooteco.wiki.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.server-url}")
    private String serverUrl;

    @Bean
    public OpenAPI roomEscapeOpenAPI() {
        String title = "CrewWiki Application Swagger";
        String description = "크루위키의 API 문서입니다.";

        Info info = new Info().title(title).description(description).version("1.0.0");

        Server server = new Server();
        server.setUrl(serverUrl);

        return new OpenAPI().info(info).servers(List.of(server));
    }
}
