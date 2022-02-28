package com.roerdev.moviesapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Configuration
@EnableOpenApi
public class SwaggerConfig implements WebMvcConfigurer {

    @Value("${swagger.enabled}")
    private boolean enable;

    @Bean
    public Docket apiRestDoc() {
        return new Docket(DocumentationType.OAS_30).pathMapping("/")
                .enable(enable)
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.roerdev.moviesapi.controllers"))
                .paths(PathSelectors.any())
                .build()
                .protocols(this.newHashSet("https", "http"));
    }

    /**
     * Información del proyecto
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger Api Doc - Alkemy")
                .description("Prueba API REST para Alkemy")
                .contact(new Contact("Erick Rodriguez", null, "erodriguezven@gmail.com"))
                .version("API Version: 0.0.1")
                .build();
    }

    @SafeVarargs
    private final <T> Set<T> newHashSet(T... ts) {
        if (ts.length > 0) {
            return new LinkedHashSet<>(Arrays.asList(ts));
        }
        return null;
    }
}
