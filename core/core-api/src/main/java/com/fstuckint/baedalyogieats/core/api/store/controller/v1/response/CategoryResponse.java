package com.fstuckint.baedalyogieats.core.api.store.controller.v1.response;

import com.fstuckint.baedalyogieats.core.api.store.domain.CategoryResult;
import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryResponse(UUID uuid, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static CategoryResponse of(CategoryResult categoryResult) {
        return new CategoryResponse(categoryResult.uuid(), categoryResult.name(), categoryResult.createdAt(),
                categoryResult.updatedAt());
    }
}
