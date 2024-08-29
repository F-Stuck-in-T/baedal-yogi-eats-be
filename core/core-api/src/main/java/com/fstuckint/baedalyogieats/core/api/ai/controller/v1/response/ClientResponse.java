package com.fstuckint.baedalyogieats.core.api.ai.controller.v1.response;

public record ClientResponse(String description) {

    public static ClientResponse of(String description) {
        return new ClientResponse(description);
    }
}
