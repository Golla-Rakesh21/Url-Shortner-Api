package com.learning.UrlShortner.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiErrorResponse(LocalDateTime timestamp,
                               int status,
                               String error,
                               String message,
                               String path,
                               String code,
                               Map<String, String> details) {
}
