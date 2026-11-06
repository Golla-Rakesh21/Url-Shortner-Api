package com.learning.UrlShortner.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

public class CreateUrlRequest {

    @NotBlank(message = "Original url is required")
    @Size(max=2048,message = "URL must not exceed 2048 characters")
    @Pattern(
            regexp = "^https?://.+$",
            message = "URL must start with http:// or https://"
    )
    public String originalUrl;

    @Size(max = 63, message = "Alias must not exceed 63 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?$",
            message = "Alias must contain only letters, numbers, and internal hyphens"
    )
    private String customAlias;

    @Future(message = "Expiration date must be in the future")
    private LocalDateTime expiresAt;


    public CreateUrlRequest() {
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getCustomAlias() {
        return customAlias;
    }

    public void setCustomAlias(String customAlias) {
        this.customAlias = customAlias;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
