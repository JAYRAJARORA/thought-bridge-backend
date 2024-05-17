package com.example.healthapp;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setPatternParser(new PathPatternParser());
        configurer.addPathPrefix("/api",
                c -> c.isAnnotationPresent(RestController.class));
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ThoughtBridge Backend APIs")
                        .version("1.0")
                        .description("These are backend APIs for the ThoughtBridge application. They cover functionalities related to therapist details, category-related details, registration and login, and discussions. " +
                                "The APIs allow users to filter and review therapists, get therapist details by ID, and find nearby therapists. " +
                                "They also support fetching categories and articles, user and therapist creation, authentication, and managing discussions, comments, and upvotes."));
    }
}
