package com.learning.UrlShortner.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "URL Shortener API",
                version = "1.0",
                description = """
                  A RESTful URL Shortener API that converts long URLs into short, shareable links while providing secure user authentication and URL management using JWT-based authorization. Users can register, log in, create and manage their shortened URLs, update URL settings, enable or disable links, and delete URLs. The API also supports seamless redirection to original destinations through publicly accessible short links, with protected endpoints ensuring that users can manage only their own URLs.
                    """
        )
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {

}