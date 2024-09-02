package com.fstuckint.baedalyogieats.core.api.store.domain;

import com.fstuckint.baedalyogieats.storage.db.core.store.CategoryEntity;
import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryResult(UUID uuid, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static CategoryResult of(CategoryEntity categoryEntity) {
        return new CategoryResult(categoryEntity.getUuid(), categoryEntity.getName(), categoryEntity.getCreatedAt(),
                categoryEntity.getUpdatedAt());
    }
}
