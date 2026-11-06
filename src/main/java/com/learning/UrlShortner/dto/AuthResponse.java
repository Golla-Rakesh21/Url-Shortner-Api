package com.learning.UrlShortner.dto;

public class AuthResponse
{
    private final String tokenType;
    private final String accessToken;
    private final String email;
    public AuthResponse(String accessToken, String email) {
        this.tokenType = "Bearer";
        this.accessToken = accessToken;
        this.email = email;
    }
    public String getTokenType() {
        return tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getEmail() {
        return email;
    }
}
