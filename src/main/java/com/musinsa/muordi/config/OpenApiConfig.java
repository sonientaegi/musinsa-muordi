package com.musinsa.muordi.config;


import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {



        Info info = new Info()
                .title("muordi 서비스 API 명세")
                .version("v0.0.1")
                .description("무(신사 코)디네이션 API 서비스에 대한 명세 입니다.");
        return new OpenAPI()
                .components(new Components())
                .servers(List.of(
                        new Server().url("http://localhost:8080"),
                        new Server().url("http://lavualvu.net:5188")
                ))
                .info(info);
    }
}
