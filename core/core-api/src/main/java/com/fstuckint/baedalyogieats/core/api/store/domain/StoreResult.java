package com.fstuckint.baedalyogieats.core.api.store.domain;

import com.fstuckint.baedalyogieats.storage.db.core.store.StoreEntity;
import java.time.LocalDateTime;
import java.util.UUID;

public record StoreResult(UUID uuid, String name, String description, String fullAddress, UUID categoryUuid,
        String categoryName, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static StoreResult of(StoreEntity storeEntity) {
        return new StoreResult(storeEntity.getUuid(), storeEntity.getName(), storeEntity.getDescription(),
                storeEntity.getFullAddress(), storeEntity.getCategoryEntity().getUuid(),
                storeEntity.getCategoryEntity().getName(), storeEntity.getCreatedAt(), storeEntity.getUpdatedAt());
    }
}
