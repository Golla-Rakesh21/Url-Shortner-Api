package com.learning.UrlShortner.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

public class UpdateUrlRequest {

        @NotBlank(message = "Original URL is required")
        @Size(max = 2048, message = "URL must not exceed 2048 characters")
        @Pattern(
                regexp = "^https?://.+$",
                message = "URL must start with http:// or https://"
        )
        private String originalUrl;

        @Future(message = "Expiration date must be in the future")
        private LocalDateTime expiresAt;

        @NotNull(message = "Active status is required")
        private Boolean active;

        public UpdateUrlRequest() {
        }

        public String getOriginalUrl() {
            return originalUrl;
        }

        public void setOriginalUrl(String originalUrl) {
            this.originalUrl = originalUrl;
        }

        public LocalDateTime getExpiresAt() {
            return expiresAt;
        }

        public void setExpiresAt(LocalDateTime expiresAt) {
            this.expiresAt = expiresAt;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }
}
