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
                    # URL Shortener API

                    A RESTful backend application that allows users
                    to convert long URLs into short, shareable links.

                    ## Key Features

                    - User Registration and Authentication
                    - JWT-based Authentication and Authorization
                    - Create Short URLs
                    - Custom Short URL Management
                    - Retrieve User-specific URLs
                    - Update URL Settings
                    - Enable or Disable Short URLs
                    - Delete Short URLs
                    - URL Redirection
                    - URL Expiration Support

                    ## Authentication

                    This API uses JWT Bearer Token authentication.

                    1. Register an account using POST /api/auth/register.
                    2. Log in using POST /api/auth/login.
                    3. Copy the JWT access token from the response.
                    4. Click the Authorize button above.
                    5. Enter your JWT token.
                    6. Execute the protected endpoints.

                    ## Public Endpoints

                    - User Registration
                    - User Login
                    - Short URL Redirection

                    ## Protected Endpoints

                    URL management operations require a valid JWT token.

                    ## Usage

                    Use the generated short URL to redirect users
                    to the original destination.

                    All responses are returned in JSON format,
                    except browser-based URL redirection.

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