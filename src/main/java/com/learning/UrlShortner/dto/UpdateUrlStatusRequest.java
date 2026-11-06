package com.learning.UrlShortner.dto;

import jakarta.validation.constraints.NotNull;

public class UpdateUrlStatusRequest {

    @NotNull(message = "Active status is required")
    private Boolean active;

    public UpdateUrlStatusRequest() {
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
