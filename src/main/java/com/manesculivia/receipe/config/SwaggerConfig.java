package com.manesculivia.receipe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

import static java.util.Arrays.asList;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, asList(
                        new ResponseBuilder()
                                .code("500")
                                .description("Internal Server Error")
                                .build(),
                        new ResponseBuilder()
                                .code("403")
                                .description("Forbidden")
                                .build()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Recipe-app REST API", "Some custom description of API.", "v1",
                "Terms of service", new Contact("Livia", "dummy-url", "dummy-email"),
                "License of API", "API license URL", Collections.emptyList());
    }
}
