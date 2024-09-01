package com.fstuckint.baedalyogieats.core.api.store.controller.v1.response;

import com.fstuckint.baedalyogieats.core.api.store.domain.StoreResult;
import java.time.LocalDateTime;
import java.util.UUID;

public record StoreResponse(UUID uuid, String name, String description, String fullAddress, UUID categoryUuid,
        String categoryName, UUID ownerUuid, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static StoreResponse of(StoreResult storeResult) {
        return new StoreResponse(storeResult.uuid(), storeResult.name(), storeResult.description(),
                storeResult.fullAddress(), storeResult.categoryUuid(), storeResult.categoryName(),
                storeResult.ownerUuid(), storeResult.createdAt(), storeResult.updatedAt());
    }
}
